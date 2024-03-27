package tank.logic;

import tank.connection.OutputConnection;
import tank.dto.TankDto;
import tank.dto.ToreDto;

import java.net.Socket;
import java.util.HashMap;

public class LogicCreateAndUse {
    public Socket input;
    public TankDto tankDto;
    public HashMap<Integer,TankDto> map;

    public LogicCreateAndUse(Socket input,  HashMap<Integer, TankDto> map) {
        this.input = input;
        this.map = map;
    }

    public HashMap<Integer,TankDto> putNewTank() {
        tankDto = new TankDto(input.getPort() + map.size());
        tankDto.setY(100* map.size());
        tankDto.tore=new ToreDto(tankDto.getY(), tankDto.getX());
        putInMap(tankDto);
        return map;
    }
    public HashMap<Integer,TankDto> putInMap(TankDto tankDto){
        map.put(tankDto.getId(),tankDto);
        OutputConnection.mapForMap.put(input.getPort(),map);
        return map;
    }
}
