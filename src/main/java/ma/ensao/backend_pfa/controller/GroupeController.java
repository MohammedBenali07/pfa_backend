package ma.ensao.backend_pfa.controller;

import jakarta.persistence.EntityNotFoundException;
import ma.ensao.backend_pfa.dto.GroupeDTO;
import ma.ensao.backend_pfa.dto.GroupeDetailDTO;
import ma.ensao.backend_pfa.dto.RemoveStudentDTO;
import ma.ensao.backend_pfa.dto.InvitationDTO;
import ma.ensao.backend_pfa.entity.Groupe;
import ma.ensao.backend_pfa.entity.Project;
import ma.ensao.backend_pfa.entity.User;
import ma.ensao.backend_pfa.repository.ProjectRepository;
import ma.ensao.backend_pfa.repository.UserRepository;
import ma.ensao.backend_pfa.service.groupe.GroupeService;
import ma.ensao.backend_pfa.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groupes")
public class GroupeController {

    @Autowired
    private GroupeService groupeService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Groupe>> getAllGroupes() {
        return new ResponseEntity<>(groupeService.listAllGroupes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupeDetailDTO> getGroupeById(@PathVariable("id") long id) {
        try {
            Groupe groupe = groupeService.getGroupeById(id);
            GroupeDetailDTO groupeDetailDTO = new GroupeDetailDTO();
            groupeDetailDTO.setId(groupe.getId());
            groupeDetailDTO.setNom(groupe.getNom());
            groupeDetailDTO.setSubject(groupe.getSubject());
            groupeDetailDTO.setProject(groupe.getProject());
            groupeDetailDTO.setRepresentant(groupe.getRepresentant());
            groupeDetailDTO.setUsers(groupe.getUsers());
            return new ResponseEntity<>(groupeDetailDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<GroupeDTO> createGroupe(@RequestBody GroupeDTO groupeDTO) {
        Groupe groupe = new Groupe();
        groupe.setNom(groupeDTO.getNom());
        groupe.setSubject(groupeDTO.getSubject());
        if (groupeDTO.getProjectId() != null) {
            Project project = projectRepository.findById(groupeDTO.getProjectId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + groupeDTO.getProjectId()));
            groupe.setProject(project);
        }
        if (groupeDTO.getRepresentantId() != null) {
            User representant = userRepository.findById(groupeDTO.getRepresentantId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + groupeDTO.getRepresentantId()));
            groupe.setRepresentant(representant);
        }
        groupeService.saveGroupe(groupe);
        groupeDTO.setId(groupe.getId());
        return new ResponseEntity<>(groupeDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGroupe(@PathVariable("id") long id) {
        groupeService.deletegroupe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Groupe>> getGroupesByProject(@PathVariable("projectId") Project project) {
        return new ResponseEntity<>(groupeService.listAllGroupesByProject(project), HttpStatus.OK);
    }

    @PutMapping("/{groupeId}/project")
    public ResponseEntity<Groupe> addProjectToGroupe(@PathVariable("groupeId") long groupeId, @RequestBody Project project) {
        try {
            Groupe groupe = groupeService.getGroupeById(groupeId);
            groupeService.addProjectToGroupe(groupe, project);
            return new ResponseEntity<>(groupe, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{groupeId}/projects")
    public ResponseEntity<List<Project>> getProjectsByGroupe(@PathVariable("groupeId") long groupeId) {
        try {
            Groupe groupe = groupeService.getGroupeById(groupeId);
            return new ResponseEntity<>(groupeService.listAllProjectsByGroupe(groupe), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{groupeId}/invite")
    public ResponseEntity<HttpStatus> inviteStudentsToGroupe(@PathVariable("groupeId") long groupeId, @RequestBody InvitationDTO invitationDTO) {
        try {
            Groupe groupe = groupeService.getGroupeById(groupeId);
            groupeService.invitestudentsToGroupe(groupe, invitationDTO.getEmails());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{groupeId}/remove")
    public ResponseEntity<HttpStatus> removeStudentFromGroupe(@PathVariable("groupeId") long groupeId, @RequestBody RemoveStudentDTO removeStudentDTO) {
        try {
            Groupe groupe = groupeService.getGroupeById(groupeId);
            groupeService.removeStudentFromGroupe(groupe, removeStudentDTO.getEmail());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
