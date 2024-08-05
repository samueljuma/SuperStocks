package com.samueljuma.superstocks.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.CompanyProfile
import utils.toBoolean
import utils.toLong

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal suspend fun insertCompanyProfile(companyProfile: CompanyProfile) {
        companyProfile.symbol?.let {
            dbQuery.insertOrReplace(
                symbol = it,
                ceo = companyProfile.ceo,
                companyName = companyProfile.companyName,
                country = companyProfile.country,
                currency = companyProfile.currency,
                description = companyProfile.description,
                exchangeShortName = companyProfile.exchangeShortName,
                image = companyProfile.image,
                industry = companyProfile.industry,
                ipoDate = companyProfile.ipoDate,
                isEtf = companyProfile.isEtf.toLong(),
                isFund = companyProfile.isFund.toLong(),
                mktCap = companyProfile.mktCap,
                price = companyProfile.price,
                sector = companyProfile.sector,
                website = companyProfile.website
            )
        }
    }

    internal suspend fun getAllCompanyProfiles(): Flow<List<CompanyProfileEntity>> {
        return flow {
            val companyProfiles = dbQuery.selectAll().executeAsList()
            emit(companyProfiles)
        }
    }
    internal suspend fun deleteAllCompanyProfiles(){
        dbQuery.deleteAll()
    }

    internal suspend fun clearAndCreateProfiles(companyProfiles: List<CompanyProfile>) {
        dbQuery.transaction {
            dbQuery.deleteAll()
            companyProfiles.forEach {
                insertCompanyProfile(it)
            }
        }
    }
}

fun CompanyProfileEntity.toCompanyProfile(): CompanyProfile {
    return CompanyProfile(
        symbol = symbol,
        ceo = ceo,
        companyName = companyName,
        country = country,
        currency = currency,
        description = description,
        exchangeShortName =  exchangeShortName,
        image = image,
        industry = industry,
        ipoDate = ipoDate,
        isEtf = isEtf.toBoolean(),
        isFund = isFund.toBoolean(),
        mktCap = mktCap,
        price = price,
        sector = sector,
        website = website
    )
}
fun CompanyProfile.toCompanyProfileEntity(): CompanyProfileEntity? {
    return symbol?.let {
        CompanyProfileEntity(
        symbol = it,
        ceo = ceo,
        companyName = companyName,
        country = country,
        currency = currency,
        description = description,
        exchangeShortName =  exchangeShortName,
        image = image,
        industry = industry,
        ipoDate = ipoDate,
        isEtf = isEtf.toLong(),
        isFund = isFund.toLong(),
        mktCap = mktCap,
        price = price,
        sector = sector,
        website = website
    )
    }
}