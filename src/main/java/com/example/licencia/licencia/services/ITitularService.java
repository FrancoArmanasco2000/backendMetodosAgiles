package com.example.licencia.licencia.services;

import com.example.licencia.licencia.dto.TitularDTO;

public interface ITitularService {

    public String create(TitularDTO titularDTO);
    public String update(TitularDTO titularDTO);
    public TitularDTO findByNroDocumento(String nroDocumento);


}
