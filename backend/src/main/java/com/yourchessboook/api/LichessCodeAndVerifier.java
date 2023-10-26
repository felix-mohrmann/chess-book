package de.yourchessboook.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichessCodeAndVerifier {
    private String code;
    private String verifier;
}
