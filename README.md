# Nearby Places Search
<i>This application uses Foursquare API in order to explore nearby places that user can visit.
It retrieves information about places around user's current location in 2km radius.</i>


App contains 3 screens:
<ul>
<li><b>Login Screen</b>: On that screen, user can login into application, using Facebook account.</li>

<li><b>Places List Screen</b>: After successful login, user can see list of nearby places, based on his current location in 2km radius. 
Each list item has photo(if exists). After that information, user also sees if that place is open/closed right now and mobile phone of that place(if exists). 
By the pressing on mobile phone number view, app makes a proposal to user to make a call by this number.</li>

<li><b>Place Details</b>: If user clicks on place item of the place list, app opens picked place details screen. 
Details screen contains the same photo in the top plus additional information about place - visits count, rating and last 3 tips about the place.</li>
</ul>

App also saves user, so after restarting the app, user would see Places list.
Logout feature - User is able to logout from the app and see Login screen.

This project uses:
<ul>
<li><b>Retrofit</b> - for retrieving JSON data from Foursquare API;</li>
<li><b>Picasso</b> - for downloading photos of places;</li>
<li><b>ButterKnife</b> - for binding views;</li>
<li><b>Facebook SDK</b> - for making login/logout feature with facebook account.</li>
</ul>

