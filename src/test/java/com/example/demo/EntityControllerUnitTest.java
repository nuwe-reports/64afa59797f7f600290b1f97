
package com.example.demo;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;



/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DoctorRepository doctorRepository;

    @Test
    void getAllDoctors_ReturnsListOfDoctors() throws Exception {
        // Arrange
        List<Doctor> doctors = Arrays.asList(
                new Doctor("John", "Doe", 35, "john.doe@example.com"),
                new Doctor("Jane", "Smith", 40, "jane.smith@example.com")
        );
        when(doctorRepository.findAll()).thenReturn(doctors);

        // Act and Assert
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    void getAllDoctors_ReturnsNoContent() throws Exception {
        // Arrange
        when(doctorRepository.findAll()).thenReturn(Collections.emptyList());

        // Act and Assert
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getDoctorById_ReturnsDoctor() throws Exception {
        // Arrange
        long doctorId = 1L;
        Doctor doctor = new Doctor("John", "Doe", 35, "john.doe@example.com");
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act and Assert
        mockMvc.perform(get("/api/doctors/{id}", doctorId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void getDoctorById_DoctorNotFound_ReturnsNotFound() throws Exception {
        // Arrange
        long doctorId = 1L;
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(get("/api/doctors/{id}", doctorId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createDoctor_CreatesDoctor() throws Exception {
        // Arrange
        Doctor doctor = new Doctor("John", "Doe", 35, "john.doe@example.com");
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        // Act and Assert
        mockMvc.perform(post("/api/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteDoctor_DeletesDoctor() throws Exception {
        // Arrange
        long doctorId = 1L;
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(new Doctor("John", "Doe", 35, "john.doe@example.com")));

        // Act and Assert
        mockMvc.perform(delete("/api/doctors/{id}", doctorId))
                .andExpect(status().isOk());
        verify(doctorRepository, times(1)).deleteById(doctorId);
    }

    @Test
    void deleteAllDoctors_DeletesAllDoctors() throws Exception {
        // Arrange
        when(doctorRepository.findAll()).thenReturn(Collections.emptyList());

        // Act and Assert
        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());


        verify(doctorRepository, times(1)).deleteAll();
    }

    @Test
    void deleteDoctor_DoctorNotFound_ReturnsNotFound() throws Exception {
        // Arrange
        long doctorId = 1L;
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(delete("/api/doctors/{id}", doctorId))
                .andExpect(status().isNotFound());
    }
}



@WebMvcTest(PatientController.class)
class PatientControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientRepository patientRepository;

    @Test
    void getAllPatients_ReturnsListOfPatients() throws Exception {
        // Arrange
        List<Patient> patients = Arrays.asList(
                new Patient("Alice", "Smith", 28, "alice@example.com"),
                new Patient("Bob", "Johnson", 35, "bob@example.com")
        );
        when(patientRepository.findAll()).thenReturn(patients);

        // Act and Assert
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("Alice"))
                .andExpect(jsonPath("$[1].firstName").value("Bob"));
    }

    @Test
    void getAllPatients_ReturnsNoContent() throws Exception {
        // Arrange
        when(patientRepository.findAll()).thenReturn(Collections.emptyList());

        // Act and Assert
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getPatientById_ReturnsPatient() throws Exception {
        // Arrange
        long patientId = 1L;
        Patient patient = new Patient("Alice", "Smith", 28, "alice@example.com");
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        // Act and Assert
        mockMvc.perform(get("/api/patients/{id}", patientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Smith"));
    }

    @Test
    void getPatientById_PatientNotFound_ReturnsNotFound() throws Exception {
        // Arrange
        long patientId = 1L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(get("/api/patients/{id}", patientId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPatient_CreatesPatient() throws Exception {
        // Arrange
        Patient patient = new Patient("Alice", "Smith", 28, "alice@example.com");
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Act and Assert
        mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated());
    }

    @Test
    void deletePatient_DeletesPatient() throws Exception {
        // Arrange
        long patientId = 1L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(new Patient("Alice", "Smith", 28, "alice@example.com")));

        // Act and Assert
        mockMvc.perform(delete("/api/patients/{id}", patientId))
                .andExpect(status().isOk());
        verify(patientRepository, times(1)).deleteById(patientId);
    }

    @Test
    void deleteAllPatients_DeletesAllPatients() throws Exception {
        // Arrange
        when(patientRepository.findAll()).thenReturn(Collections.emptyList());

        // Act and Assert
        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());


        verify(patientRepository, times(1)).deleteAll();
    }

    @Test
    void deletePatient_PatientNotFound_ReturnsNotFound() throws Exception {
        // Arrange
        long patientId = 1L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(delete("/api/patients/{id}", patientId))
                .andExpect(status().isNotFound());
    }
}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoomRepository roomRepository;

    @Test
    void getAllRooms_ReturnsListOfRooms() throws Exception {
        // Arrange
        List<Room> rooms = Arrays.asList(
                new Room("RoomA"),
                new Room("RoomB")
        );
        when(roomRepository.findAll()).thenReturn(rooms);

        // Act and Assert
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].roomName").value("RoomA"))
                .andExpect(jsonPath("$[1].roomName").value("RoomB"));
    }

    @Test
    void getAllRooms_ReturnsNoContent() throws Exception {
        // Arrange
        when(roomRepository.findAll()).thenReturn(Collections.emptyList());

        // Act and Assert
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getRoomByRoomName_ReturnsRoom() throws Exception {
        // Arrange
        String roomName = "RoomA";
        Room room = new Room(roomName);
        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.of(room));

        // Act and Assert
        mockMvc.perform(get("/api/rooms/{roomName}", roomName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.roomName").value(roomName));
    }

    @Test
    void getRoomByRoomName_RoomNotFound_ReturnsNotFound() throws Exception {
        // Arrange
        String roomName = "NonExistentRoom";
        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(get("/api/rooms/{roomName}", roomName))
                .andExpect(status().isNotFound());
    }

    @Test
    void createRoom_CreatesRoom() throws Exception {
        // Arrange
        Room room = new Room("RoomX");
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        // Act and Assert
        mockMvc.perform(post("/api/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteRoom_DeletesRoom() throws Exception {
        // Arrange
        String roomName = "RoomY";
        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.of(new Room(roomName)));

        // Act and Assert
        mockMvc.perform(delete("/api/rooms/{roomName}", roomName))
                .andExpect(status().isOk());
        verify(roomRepository, times(1)).deleteByRoomName(roomName);
    }

    @Test
    void deleteAllRooms_DeletesAllRooms() throws Exception {
        // Arrange
        when(roomRepository.findAll()).thenReturn(Collections.emptyList());

        // Act and Assert
        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());


        verify(roomRepository, times(1)).deleteAll();
    }

    @Test
    void deleteRoom_RoomNotFound_ReturnsNotFound() throws Exception {
        // Arrange
        long roomId = 1L;
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        // Act and Assert
        mockMvc.perform(delete("/api/rooms/{id}", roomId))
                .andExpect(status().isNotFound());
    }
}

