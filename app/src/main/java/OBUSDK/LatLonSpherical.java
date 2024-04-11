package OBUSDK;

public class LatLonSpherical {

        private double _lat;
        private double _lon;

    public double get_lat() {
        return _lat;
    }

    public void set_lat(double _lat) {
        this._lat = _lat;
    }

    public double get_lon() {
        return _lon;
    }

    public void set_lon(double _lon) {
        this._lon = _lon;
    }

    /*
     * Creates a latitude/longitude point on the earth’s surface, using a spherical model earth.
     *
     * @param  {number} lat - Latitude (in degrees).
     * @param  {number} lon - Longitude (in degrees).
     * @throws {TypeError} Invalid lat/lon.
     *
     * @example
     *   import LatLon from '/js/geodesy/latlon-spherical.js';
     *   const p = new LatLon(52.205, 0.119);
     */

    public LatLonSpherical(double lat, double lon)
    {
    /*if (isNaN(lat)) throw new TypeError(`invalid lat ‘${ lat }’`);
    if (isNaN(lon)) throw new TypeError(`invalid lon ‘${ lon }’`);*/

        this._lat = WrapPlus90(lat);
        this._lon = Wrap180(lon);
    }

    /*
     * Constrain degrees to range 0..360 (e.g. for bearings); -1 => 359, 361 => 1.
     *
     * @private
     * @param {number} degrees
     * @returns degrees within range 0..360.
     */
    public static double Wrap360(double degrees)
    {
        if (0 <= degrees && degrees < 360) return degrees; // avoid rounding due to arithmetic ops if within range
        return (degrees % 360 + 360) % 360; // sawtooth wave p:360, a:360
    }

    /*
     * Constrain degrees to range -180..+180 (e.g. for longitude); -181 => 179, 181 => -179.
     *
     * @private
     * @param {number} degrees
     * @returns degrees within range -180..+180.
     */
    public static double Wrap180(double degrees)
    {
        if (-180 < degrees && degrees <= 180) return degrees; // avoid rounding due to arithmetic ops if within range
        return (degrees + 540) % 360 - 180; // sawtooth wave p:180, a:±180
    }


    /*
     * Constrain degrees to range -90..+90 (e.g. for latitude); -91 => -89, 91 => 89.
     *
     * @private
     * @param {number} degrees
     * @returns degrees within range -90..+90.
     */
    public static double WrapPlus90(double degrees)
    {
        if (-90 <= degrees && degrees <= 90) return degrees; // avoid rounding due to arithmetic ops if within range
        return Math.abs((degrees % 360 + 270) % 360 - 180) - 90; // triangle wave p:360 a:±90 TODO: fix e.g. -315°
    }

    public static double WrapMinus90(double degrees)
    {
        if (-90 <= degrees && degrees <= 90) return degrees; // avoid rounding due to arithmetic ops if within range
        return Math.abs((degrees % 360 + 270) % 360 - 180) - 90; // triangle wave p:360 a:±90 TODO: fix e.g. -315°
    }
}
