package carmenromano.gestione_dispositivi.repositories;

import carmenromano.gestione_dispositivi.entities.Device;
import carmenromano.gestione_dispositivi.enums.DeviceType;
import carmenromano.gestione_dispositivi.enums.StatusType;
import carmenromano.gestione_dispositivi.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device,Integer> {

}
