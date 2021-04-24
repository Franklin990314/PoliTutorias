package co.edu.poli.tutorias.security.service;

import co.edu.poli.tutorias.entity.User;
import co.edu.poli.tutorias.entity.repository.UserRepository;
import co.edu.poli.tutorias.logic.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	HttpSession session;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		User user = userRepository.findByUserName(userName);
		
		if (user == null)
			throw new UsernameNotFoundException(userName);

		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUserName(user.getUserName());
		userDTO.setRoles(user.getRoles());

		session.setAttribute("userInfo", userDTO);
 
		return new UserDetailsImpl(user);
	}

}