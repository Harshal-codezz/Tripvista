# ✈ TripVista — Your Journey, Simplified

> A premium, fully responsive travel booking website built for a college mini project. Designed to look and feel like a real production-grade travel platform.

---

## 📋 Project Overview

| Detail        | Description                                                   |
|---------------|---------------------------------------------------------------|
| **Project**   | TripVista — Travel Booking Web Application                    |
| **Tech Stack**| HTML5, CSS3, Vanilla JavaScript                               |
| **Responsive**| Mobile-first, fully responsive (320px to 1920px)              |
| **Fonts**     | Inter (headings) + Poppins (body) via Google Fonts            |
| **Icons**     | Font Awesome 6 (CDN)                                          |
| **Images**    | Unsplash (free high-quality travel images)                    |

---

## 🎨 Color Palette

| Color         | Hex       | Usage                              |
|---------------|-----------|------------------------------------|
| Primary Blue  | `#1A73E8` | Links, branding, trust elements    |
| Orange CTA    | `#FF6B35` | Buttons, calls-to-action           |
| Background    | `#F8FAFF` | Page background                    |
| Dark Navy     | `#1C1C2E` | Text, footer                       |
| Accent Green  | `#00C851` | Prices, success states             |
| Light Gray    | `#F1F3F4` | Alternate section backgrounds      |

---

## 📁 File Structure

```
tripvista/
├── index.html              # Main homepage (10 sections)
├── css/
│   └── style.css           # All custom styles (~1100 lines)
├── js/
│   └── main.js             # All JavaScript (~200 lines)
├── pages/
│   ├── flights.html        # Flight search results page
│   ├── hotels.html         # Hotel search results page
│   └── packages.html       # Holiday packages listing
└── README.md               # This file
```

---

## 🏠 Homepage Sections

1. **Sticky Navigation Bar** — Transparent → white on scroll, mobile hamburger menu
2. **Hero Section** — Full-screen with animated gradient overlay & glassmorphism search widget (5 tabs: Flights, Hotels, Trains, Buses, Packages)
3. **Marquee Deal Strip** — Auto-scrolling deals on gradient strip
4. **Popular Destinations** — 8 destination cards (Goa, Manali, Rajasthan, Kerala, Andaman, Dubai, Bali, Bangkok)
5. **Our Services** — 6 service cards (Domestic/International Flights, Hotels, Trains, Buses, Packages)
6. **Hot Deals & Offers** — 4 deal cards with live countdown timers
7. **Why Choose TripVista** — 6 trust-building feature cards
8. **Traveler Testimonials** — 3 reviews with star ratings
9. **Mobile App Download** — CTA section with CSS phone mockup
10. **Newsletter + Footer** — Email subscribe form & 4-column footer

---

## ⚡ JavaScript Features

| Feature                       | Description                                                  |
|-------------------------------|--------------------------------------------------------------|
| Tab Switching                 | Hero search widget with 5 animated tabs                      |
| Sticky Navbar                 | Transparent → solid white with shadow on scroll              |
| Countdown Timers              | Live HH:MM:SS countdown on deal cards                        |
| Smooth Scroll                 | All anchor links scroll smoothly to sections                 |
| Mobile Hamburger Menu         | Slide-in drawer with overlay                                 |
| City Swap                     | Swap From/To fields with rotation animation                  |
| Date Picker                   | Min date auto-set to today via JavaScript                    |
| Scroll-to-Top                 | Floating button appears after 300px scroll                   |
| Animated Counters             | Numbers animate from 0 using Intersection Observer           |
| Fade-in on Scroll             | All sections fade in when scrolled into view                 |
| Radio Pill Toggle             | Trip type selector (One Way / Round Trip / Multi City)       |
| Search Loading Spinner        | Loading animation on search button click                     |
| Newsletter Subscribe          | Success feedback with color change                           |

---

## 🚀 How to Run

1. Clone or download this folder
2. Open `index.html` in any modern browser
3. No build tools, servers, or dependencies needed

> 💡 For best experience, use Google Chrome or Mozilla Firefox.

---

## 📱 Responsive Breakpoints

- **Desktop**: 1024px and above
- **Tablet**: 768px – 1024px
- **Mobile**: 480px – 768px
- **Small Mobile**: Below 480px

---

## 🛠 Design Highlights

- **Glassmorphism** — The hero search widget uses backdrop-filter blur with translucent backgrounds
- **CSS Grid + Flexbox** — No Bootstrap, no jQuery — pure CSS layout
- **Custom Scrollbar** — Styled scrollbar matching the blue theme
- **Smooth Transitions** — Cubic-bezier easing on all hover effects
- **Intersection Observer** — Efficient scroll-based animations (no scroll event spam)
- **BEM-like Naming** — Clean, readable CSS class naming convention
- **Well-Commented Code** — Every section clearly labeled for easy understanding in viva

---

## 👨‍💻 Developed By

Built as a **Semester 6 Mini Project** — Web Development

---

## 📄 License

This project is for educational purposes only.

---

*Made with ❤️ in India*
