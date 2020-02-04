package com.youssary.listaccount.repository


import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.youssary.listaccount.database.ListDB
import com.youssary.listaccount.model.repository.ListRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ListRepositoryTes() {

    @Mock
    lateinit var listRepository: ListRepository

    @Test
    fun `getList gets from local data source first`() {
        runBlocking {

            val localList = listOf(mockedList.copy(1))
            whenever(listRepository.findListRoom()).thenReturn(localList)

            val result = listRepository.findListRoom()

            assertEquals(localList, result)
        }
    }


      @Test
    fun `List saves remote data to local`() {
        runBlocking {

            val remoteList:List<ListDB> = listOf(mockedList.copy(2))
            whenever(listRepository.findListRoom()).thenReturn(remoteList)

            listRepository.findListRoom()

            verify(listRepository).findListRoom()
        }
    }


    @Test
    fun `findById calls local data source`() {
        runBlocking {

            val list = mockedList.copy(id = 5)
            whenever(listRepository.findById(5)).thenReturn(list)
            val result = listRepository.findById(5)

            assertEquals(list, result)
        }
    }

    private val mockedList = ListDB(
        0,
        12,
        "2020-01-01",
        101.0,
        15.0,
        "descrip",
        true

    )
}