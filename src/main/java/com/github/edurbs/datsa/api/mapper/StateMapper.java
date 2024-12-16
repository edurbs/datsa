package com.github.edurbs.datsa.api.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.dto.input.StateInput;
import com.github.edurbs.datsa.api.dto.output.StateOutput;
import com.github.edurbs.datsa.domain.model.State;

@Component
public class StateMapper {

    @Autowired
    private ModelMapper modelMapper;

    public State toDomain(StateInput stateInput) {
        return modelMapper.map(stateInput, State.class);
    }

    public StateOutput toOutput(State state) {
        return modelMapper.map(state, StateOutput.class);
    }

    public void copyToDomain(StateInput stateInput, State state) {
        modelMapper.map(stateInput, state);
    }

    public List<StateOutput> toOutputList(List<State> states) {
        return states.stream()
                .map(this::toOutput)
                .toList();
    }
}
