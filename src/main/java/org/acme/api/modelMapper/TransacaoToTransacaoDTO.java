package org.acme.api.modelMapper;

import org.acme.api.dto.PatrimonioDTO;
import org.acme.api.dto.TransacaoDTO;
import org.acme.domain.model.Patrimonio;
import org.acme.domain.model.Transacao;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class TransacaoToTransacaoDTO extends PropertyMap<Transacao, TransacaoDTO> {

    @Override
    protected void configure() {
        using((Converter<Patrimonio, PatrimonioDTO>) ctx -> {
            return ctx.getSource() == null
                    ? null
                    : new ModelMapper().map(ctx.getSource(), PatrimonioDTO.class);
        }).map(source.getPatrimonio(), destination.getPatrimonio());
    }
}
