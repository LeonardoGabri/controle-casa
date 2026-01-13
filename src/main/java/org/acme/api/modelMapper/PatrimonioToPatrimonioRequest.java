package org.acme.api.modelMapper;

import org.acme.api.dto.ContaDTO;
import org.acme.api.request.PatrimonioRequest;
import org.acme.domain.model.Conta;
import org.acme.domain.model.Patrimonio;
import org.modelmapper.PropertyMap;

public class PatrimonioToPatrimonioRequest extends PropertyMap<Patrimonio, PatrimonioRequest> {
    @Override
    protected void configure() {
        map().setConta(String.valueOf(source.getConta().getId()));
    }
}
