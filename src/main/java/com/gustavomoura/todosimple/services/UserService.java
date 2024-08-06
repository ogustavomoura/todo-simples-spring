package com.gustavomoura.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gustavomoura.todosimple.models.User;
import com.gustavomoura.todosimple.repositories.TaskRepository;
import com.gustavomoura.todosimple.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	public User findById(Long id) {
		Optional<User> user = this.userRepository.findById(id); 
		return user.orElseThrow(() -> new RuntimeException(
				"Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()
				));
	}
	
	@Transactional // Usar sempre que for realizar escrita no banco
	public User create(User obj) {
		obj.setId(null); // Para evitar que o id possa ser enviado. Ele deve ser gerado automaticamente.
		obj = this.userRepository.save(obj);
		this.taskRepository.saveAll(obj.getTasks());
		return obj;
	}
	
	@Transactional
	public User updatePassword (User obj) {
		User newObj = findById(obj.getId());
		newObj.setPassword(obj.getPassword());
		return this.userRepository.save(newObj);
	}
	
	@Transactional
	public void delete(Long id) {
		findById(id);
		try {
			this.userRepository.deleteById(id);
		} catch(Exception e) {
			throw new RuntimeException(
					"Não é possível excluir pois há entidades relacionadas!"
					);
		}
	}
	


}
