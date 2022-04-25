package app.plantdiary.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mobiledevelopmentfoodapp.dto.Food

@Dao
interface ILocalFoodDAO {

    @Query("SELECT * FROM foods")
    fun getAllFoods() : LiveData<List<Food>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(foods: ArrayList<Food>)

    @Delete
    fun delete(food : Food)

}