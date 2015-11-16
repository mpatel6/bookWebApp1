(function($,window,document){
    $(function(){
        
        var authorBaseUrl = "AuthorController";
        
        var $document = $(document);
        var $body = $('body');
        var $authorTableBody = $('#authorTblBody');
        
        $document.ready(function(){
            console.log("document ready even fired");
            
            if($body.attr('class')==='authorList'){
                $.ajax({
                    type:'GET',
                    url: authorBaseUrl + "?action=listAjax",
                    success: function(authors) {
                        displayAuthors(authors);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("Could not get authors for this user due to: " + errorThrown.toString());
                    }
                        
                    
                });
            }
        });
        
        function displayAuthors(authors) {
            $.each(authors, function (index, author) {
                var row = '<tr class="authorListRow">' +
                        '<td>' + author.authorId + '</td>' +
                        '<td>' + author.authorName + '</td>' +
                        '<td>' + author.dateCreated + '</td>' +
                        '</tr>';
                $authorTableBody.append(row);
            });
        }
    });
}(window.jQuery, window, document));


