package tank.event;

public class ChangeTank {
    public TankDto tankDto;

    @Override
    public String toString() {
        return "ChangeTank{" +
               "tankDto=" + tankDto.toString() +
               '}';
    }
}
