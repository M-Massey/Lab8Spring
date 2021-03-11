package com.example.discoveryserver;

import javax.ws.rs.*;
import java.util.ArrayList;

@Path("library")
public class Library {
    private static ArrayList<Book> library = new ArrayList<Book>(1);


    @GET
    @Produces("application/xml")
    @Path("{isbn}")
    public Book getBook(@PathParam("isbn") String isbn){
        Book b = library.stream().filter(book1-> book1.getIsbn().equals(isbn)).findFirst().orElse(null);
        if (b!=null) {
            return b;
        }
        else {
            return null;
        }
    }

    @POST
    public void postNewBook(@FormParam("isbn") String isbn, @FormParam("title") String title, @FormParam("author") String author) {
        Book b = new Book(title, author, isbn);
        library.add(b);
    }

    @PUT
    @Path("{author}/{title}/{isbn}")
    public void updateBook(@PathParam("isbn") String isbn, @PathParam("title") String title,
                           @PathParam("author") String author){
        Book b = library.stream().filter(book1-> book1.getIsbn().equals(isbn)).findFirst().orElse(null);
        if (b!=null) {
            b.setAuthor(author);
            b.setTitle(title);
        }
        else {
            Book b1 = new Book(title, author, isbn);
            library.add(b1);
        }
    }
}
