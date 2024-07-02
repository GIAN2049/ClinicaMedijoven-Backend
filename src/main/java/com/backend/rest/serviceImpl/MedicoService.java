package com.backend.rest.serviceImpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.rest.dto.MedicoDTO;
import com.backend.rest.dto.RolDTO;
import com.backend.rest.entity.Medico;
import com.backend.rest.entity.Rol;
import com.backend.rest.entity.Usuario;
import com.backend.rest.entity.UsuarioHasRol;
import com.backend.rest.entity.UsuarioHasRolPK;
import com.backend.rest.repository.MedicoRepository;
import com.backend.rest.repository.RolRepository;
import com.backend.rest.repository.UsuarioHasRolRepository;
import com.backend.rest.repository.UsuarioRepository;

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
	
	public MedicoDTO getMedicoById(Integer idUser, Integer idDoc) {
        Medico medico = medicoRepository.getMedico(idUser, idDoc)
                                        .orElseThrow(() -> new RuntimeException("Valor no presente"));
        
        MedicoDTO medicoDto = mapper.map(medico, MedicoDTO.class);
        return medicoDto;
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
	public MedicoDTO actualizarMedico(MedicoDTO bean) {
		// Update Usuario
		Usuario usuario = usuarioRepository.findById(bean.getUsuario().getId()).orElseThrow(() -> new RuntimeException("User not found"));
		mapper.map(bean.getUsuario(), usuario);
		if (bean.getUsuario().getPassword() != null && !bean.getUsuario().getPassword().isEmpty()) {
			usuario.setPassword(passwordEncoder.encode(bean.getUsuario().getPassword()));
		}
		usuario = usuarioRepository.save(usuario);

		// Update Medico
		Medico medico = medicoRepository.findById(bean.getId()).orElseThrow(() -> new RuntimeException("Doctor not found"));
		mapper.map(bean, medico);
		medico.setUsuario(usuario);
		medico = medicoRepository.save(medico);

		// Update Roles
		usuarioHasRolRepository.deleteByUsuarioId(usuario.getId());
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
}
