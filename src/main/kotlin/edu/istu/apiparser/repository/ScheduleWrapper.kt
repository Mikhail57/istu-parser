package edu.istu.apiparser.repository

import edu.istu.apiparser.model.Class

class ScheduleWrapper(
        @Volatile
        var schedule: List<Class>
)