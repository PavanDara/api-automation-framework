package ind.payUMoney.automation.data_mappers;

import ind.payUMoney.automation.utils.DataFactory;

public class EndPointMapper {

    public String getEndPoint(String endPoint) {
        return (String) DataFactory.get(false, endPoint, "endPoints", endPoint);
    }
}
