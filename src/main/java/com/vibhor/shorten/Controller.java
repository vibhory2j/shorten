package com.vibhor.shorten;

import com.vibhor.shorten.Model.ShortenUrl;
import com.vibhor.shorten.Model.Statistics;
import com.vibhor.shorten.Service.StatisticsService;
import com.vibhor.shorten.Service.UrlConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Controller {

    private UrlConverterService urlConverterService;

    private StatisticsService statisticsService;

    @Autowired
    public Controller(UrlConverterService urlConverterService, StatisticsService statisticsService) {
        this.urlConverterService = urlConverterService;
        this.statisticsService = statisticsService;
    }

    //Request to shorten a long url
    @RequestMapping(value = "/api/shorten", method = RequestMethod.POST)
    public ResponseEntity<String> shortenUrl(@RequestBody final ShortenUrl shortenRequest, HttpServletRequest request) throws Exception{

        //check whether valid url
        UrlValidator defaultUrl = new UrlValidator();

        String uri = shortenRequest.getUrl();

        if(!defaultUrl.isValid(uri)) {
            throw new Exception("Invalid url");
        }

        String parentUrl = request.getRequestURL().toString();

        String shortUrl = urlConverterService.shortenUrl(parentUrl, uri);

        return new ResponseEntity<String>(shortUrl, HttpStatus.OK);
    }

    //Request to get statistics for all the shortened URL' accessed
    @RequestMapping(value = "/api/statistics", method = RequestMethod.GET)
    public ResponseEntity<Statistics> statisticsByUser(){
        //validate if admin user

        return new ResponseEntity<Statistics>(statisticsService.generateAllStatistics(), HttpStatus.OK);
    }

    //Request to hit a short url and get redirected actual url
    @RequestMapping(value = "/api/{id}", method = RequestMethod.GET)
    public RedirectView convertUrl(@PathVariable(value = "id") String Surl) {

        String url = null;

        try{
            url = urlConverterService.getLongURLFromID(Surl);
        }catch(Exception e) {
            e.printStackTrace();
        }

        return new RedirectView(url);
    }

    /*@RequestMapping(value = "/api/statistics/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<ShortenUrl> statisticsByUser(@PathVariable Long id){

    }*/

    /*@RequestMapping(value = "/api/statistics/shorturl/{url}", method = RequestMethod.GET)
    public ResponseEntity<ShortenUrl> statisticsByUrl(@PathVariable String url){

    }*/
}
