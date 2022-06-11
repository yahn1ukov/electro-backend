package ua.nure.andrii.yahniukov.models.dto.maintenances;

public class BaseMaintenancesDto {
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    public static int calculateDistanceInKilometer(double carLat, double carLng,
                                                   double stationLat, double stationLng) {

        double latDistance = Math.toRadians(carLat - stationLat);
        double lngDistance = Math.toRadians(carLng - stationLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(carLat)) * Math.cos(Math.toRadians(stationLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }
}
