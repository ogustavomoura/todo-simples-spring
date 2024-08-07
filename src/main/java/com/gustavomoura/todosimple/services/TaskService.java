package com.gustavomoura.todosimple.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gustavomoura.todosimple.models.Task;
import com.gustavomoura.todosimple.repositories.TaskRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	public Task findById(Long id) {
		Optional<Task> task = this.taskRepository.findById(id);
		return task.orElseThrow(() -> new RuntimeException(
				"Tarefa não encontrada! Id: " + id + ", Tipo: " + Task.class.getName()
				));
	}
	
	@Transactional
	public Task create(Task obj) {
		obj.setId(null);
		obj = this.taskRepository.save(obj);
		return obj;
	}
	
	@Transactional
	public Task update(Task obj) {
		Task newObj = findById(obj.getId());
		newObj.setDescription(obj.getDescription());
		return this.taskRepository.save(newObj);
	}
	
	@Transactional
	public void delete(Long id) {
		findById(id);
		try {
			this.taskRepository.deleteById(id);
		} catch(Exception e) {
			throw new RuntimeException(
					"Não é possível excluir pois há entidades relacionadas"
					);
		}
	}
	
	public List<Task> findAllByUserId(Long userId){
		List<Task> tasks = this.taskRepository.buscarTarefasPorIdDeUsuario(userId);
		return tasks;
	}

}
