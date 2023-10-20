package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@DataJpaTest
public class EntityUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testDoctorEntity() {
        Doctor doctor = new Doctor("John", "Doe", 35, "john.doe@example.com");
        entityManager.persist(doctor);
        entityManager.flush();

        Doctor found = entityManager.find(Doctor.class, doctor.getId());

        assertThat(found).isEqualTo(doctor);
    }

    @Test
    void testPatientEntity() {
        Patient patient = new Patient("Jane", "Smith", 28, "jane.smith@example.com");
        entityManager.persist(patient);
        entityManager.flush();

        Patient found = entityManager.find(Patient.class, patient.getId());

        assertThat(found).isEqualTo(patient);
    }

    @Test
    void testRoomEntity() {
        Room room = new Room("Exam Room 1");
        entityManager.persist(room);
        entityManager.flush();

        Room found = entityManager.find(Room.class, room.getRoomName());

        assertThat(found).isEqualTo(room);
    }

    @Test
    void testAppointmentEntity() {
        Doctor doctor = new Doctor("John", "Doe", 35, "john.doe@example.com");
        entityManager.persist(doctor);

        Patient patient = new Patient("Jane", "Smith", 28, "jane.smith@example.com");
        entityManager.persist(patient);

        Room room = new Room("Exam Room 1");
        entityManager.persist(room);

        Appointment appointment = new Appointment(patient, doctor, room, LocalDateTime.now(), LocalDateTime.now().plusHours(3));
        entityManager.persist(appointment);
        entityManager.flush();

        Appointment found = entityManager.find(Appointment.class, appointment.getId());

        assertThat(found).isEqualTo(appointment);
    }

    @Test
    void testAppointmentOverlaps() {
        Doctor doctorA = new Doctor("Doctor A", "Doe", 40, "doctorA@example.com");
        entityManager.persist(doctorA);

        Patient patientA = new Patient("Patient A", "Smith", 30, "patientA@example.com");
        entityManager.persist(patientA);

        Room roomA = new Room("Exam Room 1");
        entityManager.persist(roomA);

        LocalDateTime startA = LocalDateTime.of(2023, 10, 20, 10, 0);
        LocalDateTime endA = LocalDateTime.of(2023, 10, 20, 11, 0);

        LocalDateTime startB = LocalDateTime.of(2023, 10, 20, 10, 30);
        LocalDateTime endB = LocalDateTime.of(2023, 10, 20, 11, 30);

        LocalDateTime startC = LocalDateTime.of(2023, 10, 20, 10, 15);
        LocalDateTime endC = LocalDateTime.of(2023, 10, 20, 10, 45);

        Appointment appointmentA = new Appointment(patientA, doctorA, roomA, startA, endA);
        entityManager.persist(appointmentA);

        Appointment appointmentB = new Appointment(patientA, doctorA, roomA, startB, endB);
        Appointment appointmentC = new Appointment(patientA, doctorA, roomA, startC, endC);

        // Verify if the appointments overlap each other
        boolean isOverlappingAB = appointmentA.overlaps(appointmentB);
        boolean isOverlappingAC = appointmentA.overlaps(appointmentC);

        assertThat(isOverlappingAB).isTrue();
        assertThat(isOverlappingAC).isTrue();
    }
}
