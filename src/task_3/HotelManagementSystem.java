package task_3;

import java.util.Random;

enum Prices {
    ECONOMY(1500),
    STANDARD(3000),
    LUX(7000),
    ULTRA_LUX(12000);

    private final int price;

    Prices(int price) { this.price = price; }

    public int getPrice() { return price; }
}

abstract class Room {
    private final int roomNumber;
    private final int maxPeople;
    private final int pricePerNight;
    private boolean isBooked;

    protected Room(int roomNumber, int maxPeople, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.maxPeople = maxPeople;
        this.pricePerNight = pricePerNight;
        this.isBooked = false;
    }

    public int getRoomNumber() { return roomNumber; }
    public int getMaxPeople() { return maxPeople; }
    public int getPricePerNight() { return pricePerNight; }
    public boolean isBooked() { return isBooked; }

    protected void setBooked(boolean booked) { isBooked = booked; }
}

final class EconomyRoom extends Room {
    public EconomyRoom(int roomNumber) {
        super(roomNumber, new Random().nextInt(2) + 1, Prices.ECONOMY.getPrice());
    }
}

abstract class ProRoom extends Room {
    protected ProRoom(int roomNumber, int maxPeople, int pricePerNight) {
        super(roomNumber, maxPeople, pricePerNight);
    }
}

final class StandardRoom extends ProRoom {
    public StandardRoom(int roomNumber) {
        super(roomNumber, new Random().nextInt(3) + 2, Prices.STANDARD.getPrice());
    }
}

final class LuxRoom extends ProRoom {
    public LuxRoom(int roomNumber) {
        super(roomNumber, new Random().nextInt(2) + 1, Prices.LUX.getPrice());
    }
}

final class UltraLuxRoom extends ProRoom {
    public UltraLuxRoom(int roomNumber) {
        super(roomNumber, new Random().nextInt(4) + 2, Prices.ULTRA_LUX.getPrice());
    }
}

class RoomAlreadyBookedException extends RuntimeException {
    public RoomAlreadyBookedException(String message) {
        super(message);
    }
}

interface RoomService<T extends Room> {
    void clean(T room);
    void reserve(T room) throws RoomAlreadyBookedException;
    void free(T room);
}

class HotelRoomService implements RoomService<Room> {
    @Override
    public void clean(Room room) {
        System.out.println("Уборка комнаты №" + room.getRoomNumber());
    }

    @Override
    public void reserve(Room room) throws RoomAlreadyBookedException {
        if (room.isBooked()) {
            throw new RoomAlreadyBookedException(room.getClass().getSimpleName() + " №" + room.getRoomNumber() + " уже забронирована!");
        }
        room.setBooked(true);
        System.out.println(room.getClass().getSimpleName() + " №" + room.getRoomNumber() + " забронирована");
    }

    @Override
    public void free(Room room) {
        room.setBooked(false);
        System.out.println("Комната №" + room.getRoomNumber() + " свободна");
    }
}

interface LuxRoomService<T extends ProRoom> extends RoomService<T> {
    void foodDelivery(T room, String order);
}

class LuxRoomServiceImpl implements LuxRoomService<ProRoom> {
    @Override
    public void clean(ProRoom room) {
        System.out.println("Уборка люксовой комнаты №" + room.getRoomNumber());
    }

    @Override
    public void reserve(ProRoom room) throws RoomAlreadyBookedException {
        if (room.isBooked()) {
            throw new RoomAlreadyBookedException("Люксовая комната №" + room.getRoomNumber() + " уже забронирована!");
        }
        room.setBooked(true);
        System.out.println("Люксовая комната №" + room.getRoomNumber() + " забронирована");
    }

    @Override
    public void free(ProRoom room) {
        room.setBooked(false);
        System.out.println("Люксовая комната №" + room.getRoomNumber() + " освобождена");
    }

    @Override
    public void foodDelivery(ProRoom room, String order) {
        if (!(room instanceof LuxRoom || room instanceof UltraLuxRoom)) {
            throw new IllegalArgumentException("Доставка еды доступна только для люксовых комнат! Это " + room.getClass().getSimpleName());
        }
        System.out.println("Доставка в комнату №" + room.getRoomNumber() + ": " + order);
    }
}

public class HotelManagementSystem {
    public static void main(String[] args) {
        RoomService<Room> roomService = new HotelRoomService();
        LuxRoomService<ProRoom> luxService = new LuxRoomServiceImpl();

        Room economy = new EconomyRoom(107);
        ProRoom standard = new StandardRoom(207);
        ProRoom lux = new LuxRoom(307);
        ProRoom ultraLux = new UltraLuxRoom(407);

        testRoomService(roomService, economy);
        testRoomService(roomService, standard);
        testRoomService(roomService, lux);
        testRoomService(roomService, ultraLux);

        System.out.println("\n\tИнтерфейс LuxRoomService");

        testLuxRoomService(luxService, standard);
        testLuxRoomService(luxService, lux);
        testLuxRoomService(luxService, ultraLux);
    }

    private static void testRoomService(RoomService<Room> service, Room room) {
        try {
            System.out.println("\nИнформация о комнате №" + room.getRoomNumber() + " (тип: " + room.getClass().getSimpleName() + ")");

            service.reserve(room);

            try {
                service.reserve(room);
            } catch (RoomAlreadyBookedException e) {
                System.out.println("Ошибка, комната уже забронирована: " + e.getMessage());
            }

            service.free(room);
            service.clean(room);

        } catch (RoomAlreadyBookedException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    private static void testLuxRoomService(LuxRoomService<ProRoom> service, ProRoom room) {
        try {
            System.out.println("\nИнформация об обслуживании люксовых комнаты №" + room.getRoomNumber() + " (" + room.getClass().getSimpleName() + ")");
            service.reserve(room);
            service.clean(room);

            try {
                service.foodDelivery(room, "Сыр с виноградом");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка доставки: " + e.getMessage());
            }

            service.free(room);
        } catch (RoomAlreadyBookedException e) {
            System.out.println("Ошибка бронирования: " + e.getMessage());
        }
    }
}


