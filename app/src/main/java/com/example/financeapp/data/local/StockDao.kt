package com.example.financeapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    //to isnert to Room
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyLists(
        companyListEntities: List<CompanyListEntity>
    )

    @Query("DELETE FROM companylistentity")
    suspend fun clearCompanyList()


    //convert name/query to lower, then the % converts them to lowercase then to upper
    @Query(
        """SELECT *
            FROM companylistentity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query) == symbol
        """
    )
    suspend fun searchCompanyListing(query: String): List<CompanyListEntity>
}