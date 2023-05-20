package com.skyapi.weatherforecast.location;

import com.skyapi.weatherforecast.common.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepo extends CrudRepository<Location, String> {
}
