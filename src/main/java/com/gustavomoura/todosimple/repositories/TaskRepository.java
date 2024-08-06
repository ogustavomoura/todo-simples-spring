package com.gustavomoura.todosimple.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gustavomoura.todosimple.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	
	// Usando método padrão do Spring
	List<Task> findByUser_Id(Long id);
	
	// Usando JPQL
	@Query(value = "SELECT t "
			+ "from Task t "
			+ "WHERE t.user.id = :id")
	List<Task> buscarTarefasPorIdDeUsuario(@Param("id") Long id);
	
	// Usando SQL puro
	@Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
	List<Task> EncontrarTarefasPorIdDeUsuario(@Param("id") Long id);

}
