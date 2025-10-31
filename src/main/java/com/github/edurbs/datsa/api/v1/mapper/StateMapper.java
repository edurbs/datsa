package com.github.edurbs.datsa.api.v1.mapper;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.controller.StateController;
import com.github.edurbs.datsa.api.v1.dto.input.StateInput;
import com.github.edurbs.datsa.api.v1.dto.output.StateOutput;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StateMapper extends RepresentationModelAssemblerSupport<State, StateOutput> {

    private final ModelMapper modelMapper;
    private final LinksAdder linksAdder;
    private final MySecurity mySecurity;

    public StateMapper(ModelMapper modelMapper, LinksAdder linksAdder, MySecurity mySecurity) {
        super(StateController.class, StateOutput.class);
        this.modelMapper = modelMapper;
        this.linksAdder = linksAdder;
        this.mySecurity = mySecurity;
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
