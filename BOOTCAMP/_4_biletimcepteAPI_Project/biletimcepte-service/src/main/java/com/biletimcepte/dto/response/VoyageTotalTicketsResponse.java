package com.biletimcepte.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoyageTotalTicketsResponse {
    private int voyageID;
    private int totalSoldTickets;
}
