package com.github.edurbs.datsa.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.State;
import com.github.edurbs.datsa.domain.service.StateRegistryService;


@RestController
@RequestMapping("/states")
public class StateController {

	@Autowired
	private StateRegistryService stateRegistryService;

	@GetMapping
	public List<State> listAll() {
		return stateRegistryService.getAll();
	}


	@GetMapping("/{stateId}")
	public ResponseEntity<State> getById(@PathVariable Long stateId) {
		try {
			return ResponseEntity.ok(stateRegistryService.getById(stateId));
		} catch (ModelNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public State add(@RequestBody State state) {
		return stateRegistryService.save(state);
	}

	@PutMapping("/{stateId}")
	public ResponseEntity<State> alter(@PathVariable Long stateId, @RequestBody State state) {
		try {
			var alteredState = stateRegistryService.getById(stateId);
			BeanUtils.copyProperties(state, alteredState, "id");
			alteredState = stateRegistryService.save(alteredState);

			return ResponseEntity.ok(alteredState);
		} catch (ModelNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{stateId}")
	public ResponseEntity<Void> delete(@PathVariable Long stateId) {
		try {
			stateRegistryService.remove(stateId);
			return ResponseEntity.noContent().build();
		} catch (ModelNotFoundException e) {
			return ResponseEntity.notFound().build();
        } catch (ModelInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
	}
}
