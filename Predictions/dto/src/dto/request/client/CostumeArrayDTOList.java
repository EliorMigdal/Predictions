package dto.request.client;

import dto.DTO;
import dto.request.client.requests.TerminationInfoDTO;

import java.util.ArrayList;
import java.util.ListResourceBundle;

public class CostumeArrayDTOList extends ListResourceBundle implements DTO {
    private final  ArrayList<TerminationInfoDTO> terminationInfoDTOArrayList;

    public CostumeArrayDTOList(ArrayList<TerminationInfoDTO> terminationInfoDTOArrayList) {
        this.terminationInfoDTOArrayList = terminationInfoDTOArrayList;
    }

    @Override
    protected Object[][] getContents() {
        Object[][] contentArray = new Object[terminationInfoDTOArrayList.size()+1][2];
        contentArray[0][0] = "Size";
        contentArray[0][1] = terminationInfoDTOArrayList.size();
        for (int i = 0; i < terminationInfoDTOArrayList.size(); i++) {
            TerminationInfoDTO dto = terminationInfoDTOArrayList.get(i);
            contentArray[i+1][0] = "Item " + (i+1);
            contentArray[i+1][1] = dto;
        }

        return contentArray;
    }
}
