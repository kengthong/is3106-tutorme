import React from "react";
import logo from "../../assets/logo.jpg";

const Header = () => {
  return (
    <div className="navbar navbar-inverse navbar-fixed-top">
      <div className="container">
        <div className="col-lg-3 col-sm-2">
          <div className="navbar-header">
            <button
              type="button"
              className="navbar-toggle"
              data-toggle="collapse"
              data-target="#bs-example-navbar-collapse-1"
            >
              <span className="sr-only">Toggle navigation</span>{" "}
              <span className="icon-bar"></span>{" "}
              <span className="icon-bar"></span>{" "}
              <span className="icon-bar"></span>
            </button>
            <a className="navbar-brand" href="/">
              <img
                src={logo}
                alt="logo"
                style={{ width: "70px", height: "70px" }}
              />
            </a>
          </div>
        </div>
        <div
          className="col-lg-7  col-md-8 col-sm-8"
          style={{ paddingTop: "20px" }}
        >
          <div
            className="collapse navbar-collapse"
            id="bs-example-navbar-collapse-1"
          >
            <ul className="nav navbar-nav">
              <li>
                <a href="/">Home</a>
              </li>
              <li>
                <a href="/search">Search</a>
              </li>
              <li>
                <a href="#">Shop</a>
              </li>
              <li>
                <a href="blog.html">Blog</a>
              </li>
              <li className="dropdown">
                <a className="dropdown-toggle" data-toggle="dropdown" href="#">
                  Pages
                </a>
                <ul className="dropdown-menu" role="menu">
                  <li>
                    <a href="product_page.html">Product Page</a>
                  </li>
                  <li>
                    <a href="product_details.html">Product Detail</a>
                  </li>
                  <li>
                    <a href="blog.html">Blog</a>
                  </li>
                  <li>
                    <a href="blog_details.html">Blog Detail</a>
                  </li>
                  <li>
                    <a href="cart_page.html">Our Cart Page</a>
                  </li>
                  <li>
                    <a href="checkout.html">Checkout</a>
                  </li>
                </ul>
              </li>
              <li>
                <a href="contact_us.html">Contact us</a>
              </li>
            </ul>
          </div>
        </div>
        <div className="col-lg-2 col-sm-2" style={{ paddingTop: "20px" }}>
          <div className="cart-area">
            <a href="cart_page.html">
              <h3>
                <span>2</span>
              </h3>
            </a>
            <div>
              <a href="cart_page.html">
                <h4>My Cart</h4>
                <h5>$29.00</h5>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Header;
