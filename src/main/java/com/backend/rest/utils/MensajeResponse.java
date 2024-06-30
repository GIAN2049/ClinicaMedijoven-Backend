package com.backend.rest.utils;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
public class MensajeResponse  implements Serializable {

    private String mensaje;
    private Object object;

}
