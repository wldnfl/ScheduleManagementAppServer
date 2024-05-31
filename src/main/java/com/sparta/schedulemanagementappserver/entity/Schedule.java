package com.sparta.schedulemanagementappserver.entity;

import com.sparta.schedulemanagementappserver.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column
    @Length(max = 200)
    @NotBlank
    private String title;

    @Column
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

    public void update(ScheduleRequestDto requestDto) {
        if (requestDto.getTitle() != null && !requestDto.getTitle().isBlank()) {
            this.title = requestDto.getTitle();
        }

        if (requestDto.getContent() != null && !requestDto.getContent().isBlank()) {
            this.content = requestDto.getContent();
        }

        if (requestDto.getPassword() != null && !requestDto.getPassword().isBlank()) {
            this.password = requestDto.getPassword();
        }

        if (requestDto.getUsername() != null && !requestDto.getUsername().isBlank()) {
            this.username = requestDto.getUsername();
        }
    }
}
