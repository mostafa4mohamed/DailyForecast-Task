package com.my_app.data.mapper

import com.my_app.data.room.entity.CloudsRoomEntity
import com.my_app.data.room.entity.DailyForecastRoomEntity
import com.my_app.data.room.entity.MainRoomEntity
import com.my_app.data.room.entity.SysRoomEntity
import com.my_app.data.room.entity.WeatherRoomEntity
import com.my_app.data.room.entity.WindRoomEntity
import com.my_app.data.utils.GsonUtils.toClassValue
import com.my_app.data.utils.GsonUtils.toStringValue
import com.my_app.domain.entities.Clouds
import com.my_app.domain.entities.CurrentWeather
import com.my_app.domain.entities.Main
import com.my_app.domain.entities.Sys
import com.my_app.domain.entities.Weather
import com.my_app.domain.entities.Wind

object DailyForecastMapper {

    fun CurrentWeather.toRoomEntity() = DailyForecastRoomEntity(
        city_name = this.city_name ?: "",
        clouds = if (this.clouds != null) CloudsRoomEntity(this.clouds!!.all) else null,
        dt = this.dt ?: 0,
        dt_txt = this.dt_txt,
        main = if (this.main != null) MainRoomEntity(
            this.main!!.feels_like,
            this.main!!.grnd_level,
            this.main!!.humidity,
            this.main!!.pressure,
            this.main!!.sea_level,
            this.main!!.temp,
            this.main!!.temp_kf,
            this.main!!.temp_max,
            this.main!!.temp_min,
        ) else null,
        pop = this.pop,
        sys = if (this.sys != null) SysRoomEntity(this.sys!!.pod) else null,
        visibility = this.visibility,
        weather = this.weather?.map { w ->
            WeatherRoomEntity(
                w.description,
                w.icon,
                w.id,
                w.main,
            )
        },
        wind = if (this.wind != null) WindRoomEntity(
            this.wind!!.deg,
            this.wind!!.gust,
            this.wind!!.speed,
        ) else null,
    )

    fun DailyForecastRoomEntity.toDomainEntity() = CurrentWeather(
        city_name = this.city_name,
        clouds = if (this.clouds != null) Clouds(this.clouds.all) else null,
        dt = this.dt,
        dt_txt = this.dt_txt,
        main = if (this.main != null) Main(
            this.main.feels_like,
            this.main.grnd_level,
            this.main.humidity,
            this.main.pressure,
            this.main.sea_level,
            this.main.temp,
            this.main.temp_kf,
            this.main.temp_max,
            this.main.temp_min,
        ) else null,
        pop = this.pop,
        sys = if (this.sys != null) Sys(this.sys.pod) else null,
        visibility = this.visibility,
        weather = this.weather?.map { w ->
            Weather(
                w.description,
                w.icon,
                w.id,
                w.main,
            )
        },
        wind = if (this.wind != null) Wind(
            this.wind.deg,
            this.wind.gust,
            this.wind.speed,
        ) else null,
    )

    fun CurrentWeather.toStringData()=this.toStringValue()

    fun String.toDataClass()=this.toClassValue<CurrentWeather>()

}