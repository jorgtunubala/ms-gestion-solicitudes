package com.maestria.gestionSolicitudes.RegistroSolicitudes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import com.maestria.gestionSolicitudes.Util.TestUtils;
import com.maestria.gestionSolicitudes.domain.FirmaSolicitud;
import com.maestria.gestionSolicitudes.domain.HistorialEstadoSolicitudes;
import com.maestria.gestionSolicitudes.domain.Solicitudes;
import com.maestria.gestionSolicitudes.domain.TiposSolicitud;
import com.maestria.gestionSolicitudes.dto.client.InformacionPersonalDto;
import com.maestria.gestionSolicitudes.dto.rest.request.InfoAdicionAsignaturaRequest;
import com.maestria.gestionSolicitudes.dto.rest.request.SolicitudRequestDto;
import com.maestria.gestionSolicitudes.repository.FirmaSolicitudRepository;
import com.maestria.gestionSolicitudes.repository.HistorialEstadoSolicitudesRepository;
import com.maestria.gestionSolicitudes.repository.SolicitudesRepository;
import com.maestria.gestionSolicitudes.repository.TiposSolicitudRepository;
import com.maestria.gestionSolicitudes.service.client.GestionDocentesEstudiantesService;
import com.maestria.gestionSolicitudes.service.rest.impl.AdicionAsignaturaServiceImpl;
import com.maestria.gestionSolicitudes.service.rest.impl.GestionSolicitudesServiceImpl;

@SpringBootTest
public class ReconocimientoCreditosPasantiaInvTest {
    
    @Autowired
    private GestionSolicitudesServiceImpl gestionSolicitudesService;

    @MockBean
    private SolicitudesRepository solicitudesRepository;

    @MockBean
    private TiposSolicitudRepository tiposSolicitudRepository;

    @MockBean
    private AdicionAsignaturaServiceImpl adicionAsignaturaService;
    @MockBean
    private FirmaSolicitudRepository firmaSolicitudRepository;
    @MockBean
    private HistorialEstadoSolicitudesRepository historialEstadoSolicitudesRepository;
    @MockBean
    private GestionDocentesEstudiantesService gestionDocentesEstudiantesService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void registrarSolicitudReconocimientoCreditosPasantiaInvConExito() throws Exception {
        // Given
        SolicitudRequestDto solicitudDto = crearSolicitudDto(1);

        TiposSolicitud tipoSolicitud = TestUtils.crearTiposSolicitudMock(1,"AD_ASIG", "Adición de Asignaturas");        
        when(tiposSolicitudRepository.findById(anyInt())).thenReturn(Optional.of(tipoSolicitud));


        Solicitudes solicitud = TestUtils.crearSolicitudMock(tipoSolicitud);
        when(solicitudesRepository.findById(anyInt())).thenReturn(Optional.of(solicitud));        
        when(solicitudesRepository.save(any(Solicitudes.class))).thenReturn(solicitud);

        // Configura el mock de registrarAdicionAsignaturas para que devuelva true (o el valor esperado)
        when(adicionAsignaturaService.registrarAdicionAsignaturas(solicitud, solicitudDto.getDatosAdicionAsignatura())).thenReturn(true);
        
        // Crear un objeto mock de FirmaSolicitud
        FirmaSolicitud firmaSolicitudMock = TestUtils.crearFirmaSolicitudMock(solicitud);

        // Configurar el comportamiento del repositorio para que devuelva el mock al buscar por solicitud
        when(firmaSolicitudRepository.findBySolicitud(solicitud)).thenReturn(firmaSolicitudMock);
        when(firmaSolicitudRepository.save(any(FirmaSolicitud.class))).thenReturn(firmaSolicitudMock);


        HistorialEstadoSolicitudes historialEstadoSolicitudes = new HistorialEstadoSolicitudes();
        historialEstadoSolicitudes.setId(1);
        historialEstadoSolicitudes.setEstado("Creada");

        when(historialEstadoSolicitudesRepository.save(any(HistorialEstadoSolicitudes.class))).thenReturn(historialEstadoSolicitudes);

        // Mock para obtener datos del estudiante
        InformacionPersonalDto estudianteMock = new InformacionPersonalDto();
        estudianteMock.setNombres("Ana");
        estudianteMock.setApellidos("Gómez");
        when(gestionDocentesEstudiantesService.obtenerInformacionEstudiantePorId(anyInt())).thenReturn(estudianteMock);


        // Mock para obtener datos del tutor
        InformacionPersonalDto tutorMock = new InformacionPersonalDto();
        tutorMock.setNombres("Ana");
        tutorMock.setApellidos("Gómez");
        when(gestionDocentesEstudiantesService.obtenerTutor(eq("456"))).thenReturn(tutorMock);

        // Mock para obtener datos del director
        InformacionPersonalDto directorMock = new InformacionPersonalDto();
        directorMock.setNombres("Carlos");
        directorMock.setApellidos("López");
        when(gestionDocentesEstudiantesService.obtenerTutor(anyString())).thenReturn(directorMock);        

        // When
        String radicado = gestionSolicitudesService.registrarSolicitud(solicitudDto);

        // Then
        verify(solicitudesRepository).save(any(Solicitudes.class));
        // ... verificar llamadas a otros métodos y valores devueltos
        assertNotNull(radicado);
    }

    @Test
    void registrarSolicitudReconocimientoCreditosPasantiaInvSinFirma() throws Exception {
        // Given
        SolicitudRequestDto solicitudDto = crearSolicitudDto(1);

        TiposSolicitud tipoSolicitud = TestUtils.crearTiposSolicitudMock(1,"AD_ASIG", "Adición de Asignaturas");        
        when(tiposSolicitudRepository.findById(anyInt())).thenReturn(Optional.of(tipoSolicitud));


        Solicitudes solicitud = TestUtils.crearSolicitudMock(tipoSolicitud);
        when(solicitudesRepository.findById(anyInt())).thenReturn(Optional.of(solicitud));        
        when(solicitudesRepository.save(any(Solicitudes.class))).thenReturn(solicitud);

        // Configura el mock de registrarAdicionAsignaturas para que devuelva true (o el valor esperado)
        when(adicionAsignaturaService.registrarAdicionAsignaturas(solicitud, solicitudDto.getDatosAdicionAsignatura())).thenReturn(true);
        
        // Crear un objeto mock de FirmaSolicitud
        FirmaSolicitud firmaSolicitudMock = TestUtils.crearFirmaSolicitudMock(solicitud);
        firmaSolicitudMock.setFirmaEstudiante(null);

        // Configurar el comportamiento del repositorio para que devuelva el mock al buscar por solicitud
        when(firmaSolicitudRepository.findBySolicitud(solicitud)).thenReturn(firmaSolicitudMock);

        HistorialEstadoSolicitudes historialEstadoSolicitudes = new HistorialEstadoSolicitudes();
        historialEstadoSolicitudes.setId(1);
        historialEstadoSolicitudes.setEstado("Creada");

        when(historialEstadoSolicitudesRepository.save(any(HistorialEstadoSolicitudes.class))).thenReturn(historialEstadoSolicitudes);

        // Mock para obtener datos del estudiante
        InformacionPersonalDto estudianteMock = new InformacionPersonalDto();
        estudianteMock.setNombres("Ana");
        estudianteMock.setApellidos("Gómez");
        when(gestionDocentesEstudiantesService.obtenerInformacionEstudiantePorId(anyInt())).thenReturn(estudianteMock);


        // Mock para obtener datos del tutor
        InformacionPersonalDto tutorMock = new InformacionPersonalDto();
        tutorMock.setNombres("Ana");
        tutorMock.setApellidos("Gómez");
        when(gestionDocentesEstudiantesService.obtenerTutor(eq("456"))).thenReturn(tutorMock);

        // Mock para obtener datos del director
        InformacionPersonalDto directorMock = new InformacionPersonalDto();
        directorMock.setNombres("Carlos");
        directorMock.setApellidos("López");
        when(gestionDocentesEstudiantesService.obtenerTutor(anyString())).thenReturn(directorMock);        

        // When
        String radicado = gestionSolicitudesService.registrarSolicitud(solicitudDto);

        // Then
        verify(solicitudesRepository).save(any(Solicitudes.class));
        // ... verificar llamadas a otros métodos y valores devueltos
        assertNotNull(radicado);
    }

    @Test
    void registrarSolicitudReconocimientoCreditosPasantiaInvTutorNoAsignado() throws Exception {
        // Given
        SolicitudRequestDto solicitudDto = crearSolicitudDto(1);

        TiposSolicitud tipoSolicitud = TestUtils.crearTiposSolicitudMock(1,"AD_ASIG", "Adición de Asignaturas");        
        when(tiposSolicitudRepository.findById(anyInt())).thenReturn(Optional.of(tipoSolicitud));


        Solicitudes solicitud = TestUtils.crearSolicitudMock(tipoSolicitud);
        solicitud.setIdTutor(null);
        when(solicitudesRepository.findById(anyInt())).thenReturn(Optional.of(solicitud));        

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            gestionSolicitudesService.registrarSolicitud(solicitudDto);
        });

        // Then
        assertNotNull(exception);
        assertEquals("Error al guardar la solicitud.", exception.getMessage());
    }

    @Test
    void registrarSolicitudReconocimientoCreditosPasantiaInvTipoSolicitudIncorrecto() throws Exception {
        // Given
        SolicitudRequestDto solicitudDto = crearSolicitudDto(1);

        TiposSolicitud tipoSolicitud = TestUtils.crearTiposSolicitudMock(1,"AD_ASIG", "Adición de Asignaturas"); 
        tipoSolicitud.setId(null);
        solicitudDto.setIdTipoSolicitud(null);

        Solicitudes solicitud = TestUtils.crearSolicitudMock(tipoSolicitud);
        when(solicitudesRepository.findById(anyInt())).thenReturn(Optional.of(solicitud));        
        when(solicitudesRepository.save(any(Solicitudes.class))).thenReturn(solicitud);

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            gestionSolicitudesService.registrarSolicitud(solicitudDto);
        });

        // Then
        assertNotNull(exception);
        assertEquals("Error al registrar la solicitud.", exception.getMessage());
    }

    @Test
    void registrarSolicitudReconocimientoCreditosPasantiaInvTutorNoDisponible() throws Exception {
        // Given
        SolicitudRequestDto solicitudDto = crearSolicitudDto(1);

        TiposSolicitud tipoSolicitud = TestUtils.crearTiposSolicitudMock(1,"AD_ASIG", "Adición de Asignaturas");        
        when(tiposSolicitudRepository.findById(anyInt())).thenReturn(Optional.of(tipoSolicitud));


        Solicitudes solicitud = TestUtils.crearSolicitudMock(tipoSolicitud);
        solicitud.setIdTutor(null);
        when(solicitudesRepository.findById(anyInt())).thenReturn(Optional.of(solicitud));        

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            gestionSolicitudesService.registrarSolicitud(solicitudDto);
        });

        // Then
        assertNotNull(exception);
        assertEquals("Error al guardar la solicitud.", exception.getMessage());
    }

    @Test
    void registrarSolicitudReconocimientoCreditosPasantiaInvCamposIncompletos() {
        // Given
        SolicitudRequestDto solicitudDto = crearSolicitudDto(1);
        solicitudDto.setIdEstudiante(null);

        TiposSolicitud tipoSolicitud = TestUtils.crearTiposSolicitudMock(1, "AD_ASIG", "Adición de Asignaturas");
        when(tiposSolicitudRepository.findById(anyInt())).thenReturn(Optional.of(tipoSolicitud));


        Solicitudes solicitud = TestUtils.crearSolicitudMock(tipoSolicitud);
        when(solicitudesRepository.findById(anyInt())).thenReturn(Optional.of(solicitud));        
        when(solicitudesRepository.save(any(Solicitudes.class))).thenReturn(solicitud);                  

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            gestionSolicitudesService.registrarSolicitud(solicitudDto);
        });

        // Then
        assertNotNull(exception);
        assertEquals("Error al guardar la solicitud", exception.getMessage());
    }

    @Test
    void registrarSolicitudReconocimientoCreditosPasantiaInvErrorRegistrarHistorial() throws Exception {
        // Given
        SolicitudRequestDto solicitudDto = crearSolicitudDto(1);

        TiposSolicitud tipoSolicitud = TestUtils.crearTiposSolicitudMock(1,"AD_ASIG", "Adición de Asignaturas");        
        when(tiposSolicitudRepository.findById(anyInt())).thenReturn(Optional.of(tipoSolicitud));


        Solicitudes solicitud = TestUtils.crearSolicitudMock(tipoSolicitud);
        solicitud.setEstado(null);
        when(solicitudesRepository.findById(anyInt())).thenReturn(Optional.of(solicitud));                

        // Configura el mock de registrarAdicionAsignaturas para que devuelva true (o el valor esperado)
        when(adicionAsignaturaService.registrarAdicionAsignaturas(solicitud, solicitudDto.getDatosAdicionAsignatura())).thenReturn(true);
        
        // Crear un objeto mock de FirmaSolicitud
        FirmaSolicitud firmaSolicitudMock = TestUtils.crearFirmaSolicitudMock(solicitud);

        // Configurar el comportamiento del repositorio para que devuelva el mock al buscar por solicitud
        when(firmaSolicitudRepository.findBySolicitud(solicitud)).thenReturn(firmaSolicitudMock);
        when(firmaSolicitudRepository.save(any(FirmaSolicitud.class))).thenReturn(firmaSolicitudMock);   

        // Mock para obtener datos del estudiante
        InformacionPersonalDto estudianteMock = new InformacionPersonalDto();
        estudianteMock.setNombres("Ana");
        estudianteMock.setApellidos("Gómez");
        when(gestionDocentesEstudiantesService.obtenerInformacionEstudiantePorId(anyInt())).thenReturn(estudianteMock);


        // Mock para obtener datos del tutor
        InformacionPersonalDto tutorMock = new InformacionPersonalDto();
        tutorMock.setNombres("Ana");
        tutorMock.setApellidos("Gómez");
        when(gestionDocentesEstudiantesService.obtenerTutor(eq("456"))).thenReturn(tutorMock);

        // Mock para obtener datos del director
        InformacionPersonalDto directorMock = new InformacionPersonalDto();
        directorMock.setNombres("Carlos");
        directorMock.setApellidos("López");
        when(gestionDocentesEstudiantesService.obtenerTutor(anyString())).thenReturn(directorMock);        

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            gestionSolicitudesService.registrarSolicitud(solicitudDto);
        });

        // Then
        assertNotNull(exception);
        assertEquals("Error al guardar la solicitud.", exception.getMessage());
    }

    @Test
    void registrarSolicitudErrorAlGuardarSolicitud() throws Exception {
        // Given
        SolicitudRequestDto solicitudDto = crearSolicitudDto(1);
        doThrow(new DataIntegrityViolationException("Error al guardar", new SQLException()))
            .when(solicitudesRepository).save(any(Solicitudes.class));
        
        assertThrows(Exception.class, () -> gestionSolicitudesService.registrarSolicitud(solicitudDto));
    }

    private SolicitudRequestDto crearSolicitudDto(Integer idTipoSolicitud) {
        SolicitudRequestDto solicitudDto = TestUtils.crearSolicitudRequestDto(idTipoSolicitud);        
        solicitudDto.setDatosAdicionAsignatura(List.of(new InfoAdicionAsignaturaRequest("Matemáticas", 4, "A")));        
        return solicitudDto;
    }
}