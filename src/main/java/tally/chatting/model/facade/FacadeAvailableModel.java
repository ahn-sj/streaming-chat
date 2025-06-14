package tally.chatting.model.facade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tally.chatting.model.llmclient.LlmModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacadeAvailableModel {
    private String displayName;
    private String codeName;

    public FacadeAvailableModel(final LlmModel model) {
        this.displayName = model.name();
        this.codeName = model.getCode();
    }
}
