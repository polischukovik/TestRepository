$(function() {

  // We can attach the `fileselect` event to all file inputs on the page
  $(document).on('change', ':file', function() {
    var input = $(this),
        numFiles = input.get(0).files ? input.get(0).files.length : 1,
        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');    
        
    input.trigger('fileselect', [numFiles, label]);
  });
//We can watch for our custom `fileselect` event like this
  $(document).ready(
		  function() {
		      $(':file').on('fileselect', function(event, numFiles, label) {
		          var input = $(this).parents('.input-group').find(':text'),
		              log = numFiles > 1 ? numFiles + ' files selected' : label;
		
		          if( input.length ) {
		              input.val(log);
		          } else {
		              if( log ) alert(log);
		          }
		      });
		      
		      $(':file').on('loadFile', function(e) {
		    	  console.log(e.target.files)
		    	  var input = e.target;
		    		if (input.files && input.files[0]) {
		    	        var reader = new FileReader();		    	        
		    	        reader.onload = function (e) {
		    	        	$('#' + input.id.replace("file-","file-data-")).data( "file-content", data );
		    	        }		    	        
		    	        reader.readAsText(input.files[0]);
		    	    }
		      });
		  }
  );
});

function createClick() {
	$(':file').trigger('loadFile', [this]);
	
	//window.location.href = "#";
}

function readFile(input1){
	
}