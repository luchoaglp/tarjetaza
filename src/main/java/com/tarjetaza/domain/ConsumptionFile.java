package com.tarjetaza.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "consumption_files")
public class ConsumptionFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumption_file_id")
    private Long id;

    @Size(max = 10)
    @Column(name = "file_name", nullable = false)
    private String fileName;

    @JsonProperty("process_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "process_date", nullable = false)
    private LocalDate processDate;

    @Column(name = "number_of_records", nullable = false)
    private Integer numberOfRecords;

    @JsonIgnore
    @OneToMany(mappedBy = "consumptionFile", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ConsumptionFileRecord> consumptionFileRecords = new ArrayList<>();

    @JsonIgnore
    @NotNull
    @CreatedDate
    private LocalDateTime createdDate;

    public ConsumptionFile() {
        this.createdDate = LocalDateTime.now();
    }

    public void addRecord(ConsumptionFileRecord record) {
        record.setConsumptionFile(this);
        this.getConsumptionFileRecords().add(record);
    }

    @Override
    public String toString() {
        return "ConsumptionFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", processDate=" + processDate +
                ", numberOfRecords=" + numberOfRecords +
                ", createdDate=" + createdDate +
                '}';
    }

}
