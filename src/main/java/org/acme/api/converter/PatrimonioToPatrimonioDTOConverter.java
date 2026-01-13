package org.acme.api.converter;

import org.acme.api.dto.PatrimonioDTO;
import org.acme.domain.model.Patrimonio;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class PatrimonioToPatrimonioDTOConverter implements Converter<Patrimonio, PatrimonioDTO> {

    @Override
    public PatrimonioDTO convert(MappingContext<Patrimonio, PatrimonioDTO> mappingContext) {
        Patrimonio source = mappingContext.getSource();
        if (source == null) {
            return null;
        }

        PatrimonioDTO dto = new PatrimonioDTO();
        dto.setId(source.getId());
        dto.setTipo(source.getTipo());
        dto.setValor(source.getValor());
        dto.setDataInicio(source.getDataInicio());
        dto.setDataFim(source.getDataFim());
        dto.setDescricao(source.getDescricao());
        dto.setConta(source.getConta());
        dto.setMoeda(source.getMoeda());
        return dto;    }
}
