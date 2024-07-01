package carmenromano.gestione_dispositivi.services;

import carmenromano.gestione_dispositivi.entities.Device;
import carmenromano.gestione_dispositivi.entities.Employee;
import carmenromano.gestione_dispositivi.enums.DeviceType;
import carmenromano.gestione_dispositivi.enums.StatusType;
import carmenromano.gestione_dispositivi.exceptions.BadRequestException;
import carmenromano.gestione_dispositivi.exceptions.NotFoundException;
import carmenromano.gestione_dispositivi.payloads.DeviceAssignmentPayload;
import carmenromano.gestione_dispositivi.payloads.DevicePayload;
import carmenromano.gestione_dispositivi.payloads.EmployeePayload;
import carmenromano.gestione_dispositivi.repositories.DeviceRepository;

import carmenromano.gestione_dispositivi.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    public Device save(DevicePayload body) throws IOException {

        Device device = new Device();
        device.setStatus(body.status());
        device.setType(body.type());

        return deviceRepository.save(device);
    }
   

    public Page<Device> getAllDevice(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return deviceRepository.findAll(pageable);
    }

    public Device findById(int id) {
        return deviceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) {
        Device found = this.findById(id);
        deviceRepository.delete(found);
    }


    public Device findByIdAndUpdate(int id, Device body) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (deviceOptional.isPresent()) {
            Device found = deviceOptional.get();
            found.setStatus(body.getStatus());
            found.setType(body.getType());
            return deviceRepository.save(found);
        } else {
            throw new NotFoundException(id);
        }
    }
    public Device assignDeviceToEmployee(int deviceId, DeviceAssignmentPayload payload) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        Optional<Employee> employeeOptional = employeeRepository.findById(payload.employeeId());

        if (deviceOptional.isPresent() && employeeOptional.isPresent()) {
            Device found = deviceOptional.get();
            Employee foundEmployee = employeeOptional.get();
            if (found.getEmployee() != null) {
                throw new BadRequestException("Il dispositivo " + found.getId() +" è già stato assegnato a questo dipendente");
            }
            found.setEmployee(foundEmployee);
            found.setStatus(StatusType.ASSIGNED);
            return deviceRepository.save(found);
        } else {
            throw new NotFoundException(deviceId);
        }
    }



}
