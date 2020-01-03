package com.gavinflood.saver.controller

import com.gavinflood.saver.service.SampleDataLoaderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller to load sample data.
 */
@RestController
@RequestMapping("/api/sampledata")
class SampleDataLoaderController(private val sampleDataLoaderService: SampleDataLoaderService) {

    /**
     * Load sample data.
     */
    @PostMapping
    fun loadSampleData(): ResponseEntity<String> {
        sampleDataLoaderService.loadSampleData()
        return ResponseEntity.ok().build()
    }

}