package com.maestria.gestionSolicitudes.TiposSolicitudes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.maestria.gestionSolicitudes.Util.TestUtils;
import com.maestria.gestionSolicitudes.domain.TiposSolicitud;
import com.maestria.gestionSolicitudes.dto.rest.request.TipoSolicitudDto;
import com.maestria.gestionSolicitudes.repository.TiposSolicitudRepository;
import com.maestria.gestionSolicitudes.service.rest.impl.GestionSolicitudesServiceImpl;

@SpringBootTest
public class TipoSolicitudTest {

    @Mock
    private TiposSolicitudRepository tipoSolicitudRepository;

    @InjectMocks
    private GestionSolicitudesServiceImpl tipoSolicitudService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTiposSolicitudes() {
        // Datos de entrada simulados
        TiposSolicitud tipo1 = TestUtils.crearTiposSolicitudMock(1,"AD_ASIG", "Adición de asignaturas");

        TiposSolicitud tipo2 = TestUtils.crearTiposSolicitudMock(2, "CA_ASIG", "Cancelación de asignaturas");

        // Configurar el comportamiento del mock del repositorio
        when(tipoSolicitudRepository.findByEstadoOrderByNombreAsc("ACTIVO"))
                 .thenReturn(Arrays.asList(tipo1, tipo2));

        // Llamada al método a probar
        List<TipoSolicitudDto> resultado = tipoSolicitudService.obtenerTiposSolicitudes();

        // Verificar resultados
        assertEquals(2, resultado.size(), "La lista de tipos de solicitud debe tener dos elementos");

        TipoSolicitudDto dto1 = resultado.get(0);
        assertEquals(1, dto1.getIdSolicitud());
        assertEquals("AD_ASIG", dto1.getCodigoSolicitud());
        assertEquals("Adición de asignaturas", dto1.getNombreSolicitud());

        TipoSolicitudDto dto2 = resultado.get(1);
        assertEquals(2, dto2.getIdSolicitud());
        assertEquals("CA_ASIG", dto2.getCodigoSolicitud());
        assertEquals("Cancelación de asignaturas", dto2.getNombreSolicitud());
    }
}
