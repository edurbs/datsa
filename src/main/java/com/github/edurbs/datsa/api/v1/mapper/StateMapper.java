package com.github.edurbs.datsa.api.v1.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.StateController;
import com.github.edurbs.datsa.api.v1.dto.input.StateInput;
import com.github.edurbs.datsa.api.v1.dto.output.StateOutput;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.State;

@Component
public class StateMapper extends RepresentationModelAssemblerSupport<State, StateOutput> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LinksAdder linksAdder;

    @Autowired
    private MySecurity mySecurity;

    public StateMapper() {
        super(StateController.class, StateOutput.class);
    }

    public State toDomain(StateInput stateInput) {
        return modelMapper.map(stateInput, State.class);
    }

    public @NonNull StateOutput toModel(@NonNull State state) {
        StateOutput stateOutput = createModelWithId(state.getId(), state);
        modelMapper.map(state, stateOutput);
        if(this.mySecurity.canConsultStates()){
            stateOutput.add(linksAdder.toStates("states"));
        }
        return stateOutput;
    }

    public void copyToDomain(StateInput stateInput, State state) {
        modelMapper.map(stateInput, state);
    }

    @Override
    public @NonNull CollectionModel<StateOutput> toCollectionModel(@NonNull Iterable<? extends State> entities) {
        CollectionModel<StateOutput> collectionModel = super.toCollectionModel(entities);
        if(this.mySecurity.canConsultStates()){
            collectionModel.add(linksAdder.toStates());

        }
        return collectionModel;
    }
}
