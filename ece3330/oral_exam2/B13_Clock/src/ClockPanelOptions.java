import java.time.ZoneId;

/**
 * This class represents some configurable options that are sent into the ClockPanel object.
 */
public class ClockPanelOptions {
    private final String formalLocation;
    private final ZoneId zoneID; // A ZoneID representing the provided timezone of our clock.
    private long startTime;

    /**
     * Constructs clock panel options.
     * @param formalLocation - The formal [displayed] location.
     * @param timezoneID - The timezone ID. This is a string that must be accepted by the ZoneId class.
     */
    public ClockPanelOptions(String formalLocation, String timezoneID) {
        this.formalLocation = formalLocation;
        this.zoneID = ZoneId.of(timezoneID);
        this.startTime = 1647158395L * 1000; // Sunday, March 13, 2022 @ 1:59:55 AM (in ms).
    }

    /**
     * Gets the formal location of the clock, or the displayed location.
     * @return The formal location of the clock.
     */
    public String getFormalLocation() {
        return this.formalLocation;
    }

    /**
     * Gets the ZoneID of the clock, or the timezone information.
     * @return The ZoneID.
     */
    public ZoneId getZoneID() {
        return this.zoneID;
    }

    /**
     * Gets the start time, a unix timestamp in milliseconds.
     * @return A unix timestamp of which the clock starts at.
     */
    public long getStartTime() {
        return this.startTime;
    }

    /**
     * Sets the start time.
     * @param value A unix timestamp to start the clock at.
     */
    public void setStartTime(long value) {
        this.startTime = value;
    }
}
