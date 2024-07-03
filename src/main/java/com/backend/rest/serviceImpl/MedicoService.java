package com.backend.rest.serviceImpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.rest.dto.MedicoDTO;
import com.backend.rest.dto.MedicoUpdateDTO;
import com.backend.rest.dto.RolDTO;
import com.backend.rest.entity.Especialidad;
import com.backend.rest.entity.Medico;
import com.backend.rest.entity.Rol;
import com.backend.rest.entity.Usuario;
import com.backend.rest.entity.UsuarioHasRol;
import com.backend.rest.entity.UsuarioHasRolPK;
import com.backend.rest.repository.EspecialidadRepository;
import com.backend.rest.repository.MedicoRepository;
import com.backend.rest.repository.RolRepository;
import com.backend.rest.repository.UsuarioHasRolRepository;
import com.backend.rest.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class MedicoService extends ICRUDImpl<Medico, Integer>{

	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioHasRolRepository usuarioHasRolRepository;
	
	@Autowired
	private RolRepository rolRepository;
	
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private EspecialidadRepository especialidadRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public JpaRepository<Medico, Integer> getRepository() {
		// TODO Auto-generated method stub
		return medicoRepository;
	}
	
	public Optional<MedicoDTO> getMedicoId(int idUsuario, int idMedico) {
		return medicoRepository.getMedico(idUsuario, idMedico)
                .map(m -> mapper.map(m, MedicoDTO.class));
	}
	
	public MedicoDTO obtenerMedicoId(int idMedico) {
		return medicoRepository.findById(idMedico)
                .map(m -> mapper.map(m, MedicoDTO.class)).orElse(null);
	}
	
	public Medico getMedicoById(Integer idUser, Integer idDoc) {
        Medico medico = medicoRepository.getMedico(idUser, idDoc)
                                        .orElseThrow(() -> new RuntimeException("Valor no presente"));
        return medico;
    }

	@Transactional
	public MedicoDTO registrarMedico(MedicoDTO bean) {
		// Save Usuario
		Usuario usuario = mapper.map(bean.getUsuario(), Usuario.class);
		usuario.setPassword(passwordEncoder.encode(bean.getUsuario().getPassword()));
		usuario = usuarioRepository.save(usuario);

		// Save Medico
		Medico medico = mapper.map(bean, Medico.class);
		medico.setUsuario(usuario);
		medico = medicoRepository.save(medico);

		// Save Roles
		for (RolDTO rolDTO : bean.getUsuario().getRoles()) {
			Rol rol = rolRepository.findById(rolDTO.getId()).orElseThrow(() -> new RuntimeException("Role not found"));
			UsuarioHasRol usuarioHasRol = new UsuarioHasRol();
			UsuarioHasRolPK pk = new UsuarioHasRolPK();
			pk.setId_usuario(usuario.getId());
			pk.setId_rol(rol.getId());
			usuarioHasRol.setUsuarioHasRolPk(pk);
			usuarioHasRol.setUsuario(usuario);
			usuarioHasRol.setRol(rol);
			usuarioHasRolRepository.save(usuarioHasRol);
		}

		return mapper.map(medico, MedicoDTO.class);
	}
	
	
	
	@Transactional
	public MedicoUpdateDTO actualizarMedico(MedicoUpdateDTO bean) {
		// Actualizar Usuario
	    Usuario usuario = usuarioRepository.findById(bean.getUsuario().getId())
	            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

	    // Mapea el DTO al objeto de usuario y maneja la contraseña
	    mapper.map(bean.getUsuario(), usuario);
	    if (bean.getUsuario().getPassword() != null && !bean.getUsuario().getPassword().isEmpty()) {
	        usuario.setPassword(passwordEncoder.encode(bean.getUsuario().getPassword()));
	    }
	    usuario = usuarioRepository.save(usuario);
	    
	    Medico existingMedico = medicoRepository.findById(bean.getId())
	            .orElseThrow(() -> new EntityNotFoundException("Medico not found"));
	    
	    existingMedico.setUsuario(usuario);
	    
        if (bean.getEspecialidad().getId() != null) {
            Especialidad especialidad = especialidadRepository.findById(bean.getEspecialidad().getId())
                .orElseThrow(() -> new EntityNotFoundException("Especialidad not found"));
            existingMedico.setEspecialidad(especialidad);
        }
	    		
	    medicoRepository.save(existingMedico);

	    return mapper.map(existingMedico, MedicoUpdateDTO.class);
	}
}
