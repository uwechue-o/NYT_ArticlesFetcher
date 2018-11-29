package com.example.fitness.model;

import java.util.List;

/**
 * Model class for retrieval of NYT search data results.
 *
 *
 * Created by O.Uwechue on 11/17/18.
 *
 *    ---------  Copyright 2018, Oke Uwechue, All rights reserved. ---------
 *
 *
 * This class models the top-level nodes of interest in the NYT articles JSON response.
 *
 * Below is the overall expected JSON node structure we're interested in extracting from the response:
 * (each 'page' contains TEN of these nodes: "web_url", "snippet","print_page", "blog", "source", "multimedia", "headline", "keywords", "pub_date" etc.
 *  and a SINGLE "docs" and "meta" node )
 *
 *          - the "headline" string to be displayed on home screen is extracted from 'docs/headline/main/'
 *          - the thumbnail images to be displayed on home screen is obtained from 'docs/multimedia/url'  (however, the multimedia node could be empty, so no thumbnail will display)
 *
 *
 *
 * "docs": [
 *       {
 *         "web_url": "https://www.nytimes.com/2018/03/07/books/review-behemoth-history-of-factory-joshua-b-freeman.html",
 *         "snippet": "Joshua B. Freeman traces the rise of the factory and how it became entwined with Enlightenment ideas of progress.",
 *         "print_page": "6",
 *         "blog": {},
 *         "source": "The New York Times",
 *         "multimedia": [
 *           {
 *             "rank": 0,
 *             "subtype": "xlarge",
 *             "caption": null,
 *             "credit": null,
 *             "type": "image",
 *             "url": "images/2018/03/08/books/08bookfreeman1/08bookfreeman1-articleLarge.jpg",
 *             "height": 896,
 *             "width": 600,
 *             "legacy": {
 *               "xlarge": "images/2018/03/08/books/08bookfreeman1/08bookfreeman1-articleLarge.jpg",
 *               "xlargewidth": 600,
 *               "xlargeheight": 896
 *             },
 *             "subType": "xlarge",
 *             "crop_name": "articleLarge"
 *           },
 *           {
 *             "rank": 0,
 *             "subtype": "thumbnail",
 *             "caption": null,
 *             "credit": null,
 *             "type": "image",
 *             "url": "images/2018/03/08/books/08bookfreeman1/08bookfreeman1-thumbStandard.jpg",
 *             "height": 75,
 *             "width": 75,
 *             "legacy": {
 *               "thumbnail": "images/2018/03/08/books/08bookfreeman1/08bookfreeman1-thumbStandard.jpg",
 *               "thumbnailwidth": 75,
 *               "thumbnailheight": 75
 *             },
 *             "subType": "thumbnail",
 *             "crop_name": "thumbStandard"
 *           },....
 *           .....
 *
 *          ]
 *        "headline": {
 *           "main": "In ‘Behemoth,’ Manufacturing Still Looms Large",
 *           "kicker": "Books of The Times",
 *           "content_kicker": "Books of The Times",
 *           "print_headline": "Tracing the Rise Of Manufacturing In Modern Times",
 *           "name": null,
 *           "seo": null,
 *           "sub": null
 *         },
 *         "keywords":
 *         [
 *           {
 *             "name": "creative_works",
 *             "value": "Behemoth: A History of the Factory and the Making of the Modern World (Book)",
 *             "rank": 1,
 *             "major": "N"
 *           },
 *           {
 *             "name": "persons",
 *             "value": "Freeman, Joshua B",
 *             "rank": 2,
 *             "major": "N"
 *           },...
 *           ....
 *           ],
 *         "pub_date": "2018-03-07T16:33:41+0000",
 *         "document_type": "article",
 *         "news_desk": "Culture",
 *         "byline": {
 *           "original": "By JENNIFER SZALAI",
 *           "person": [
 *             {
 *               "firstname": "Jennifer",
 *               "middlename": null,
 *               "lastname": "SZALAI",
 *               "qualifier": null,
 *               "title": null,
 *               "role": "reported",
 *               "organization": "",
 *               "rank": 1
 *             }
 *           ],
 *           "organization": null
 *         },
 *         "type_of_material": "Review",
 *         "_id": "5aa0146c5d97b3000139422c",
 *         "word_count": 1118,
 *         "score": 83.75302,
 *         "uri": "nyt://article/202a7a60-4b2e-5dac-b0f3-af9066c56bec"
 *       },
 *       {
 *         "web_url": "https://www.nytimes.com/2018/11/15/business/amazon-hq2.html",
 *       ...
 *       ....
 *
 *       }
 *     ],
 *      "meta": {
 *          "hits": 7615,
 *          "offset": 0,
 *          "time": 14
 *      }
 *
 *
 */

public class SearchResultsModel_parentNode {

    public SearchResultsModel_parentNode(){}

    // nested node data
    private List<SearchResultsModel_documentNode> docs;
    private SearchResultsModel_meta meta;

    // GETTERS
    public List<SearchResultsModel_documentNode> getDocs()
    {
        return docs;
    }
    public SearchResultsModel_meta getMeta() { return meta; }

    // SETTERS
    public void setDocs(List<SearchResultsModel_documentNode> s)
    {
        this.docs = s;
    }
    public void setMeta(SearchResultsModel_meta meta) { this.meta = meta; }

}
