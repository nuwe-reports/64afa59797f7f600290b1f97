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
    public void testDoctorEntity() {
        Doctor doctor = new Doctor("John", "Doe", 35, "john.doe@example.com");
        entityManager.persist(doctor);
        entityManager.flush();

        Doctor found = entityManager.find(Doctor.class, doctor.getId());

        assertThat(found).isEqualTo(doctor);
    }

    @Test
    public void testPatientEntity() {
        Patient patient = new Patient("Jane", "Smith", 28, "jane.smith@example.com");
        entityManager.persist(patient);
        entityManager.flush();

        Patient found = entityManager.find(Patient.class, patient.getId());

        assertThat(found).isEqualTo(patient);
    }

    @Test
    public void testRoomEntity() {
        Room room = new Room("Exam Room 1");
        entityManager.persist(room);
        entityManager.flush();

        Room found = entityManager.find(Room.class, room.getRoomName());

        assertThat(found).isEqualTo(room);
    }

    @Test
    public void testAppointmentEntity() {
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
}
