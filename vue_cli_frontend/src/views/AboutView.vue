<template>
  <div class="about-sections">
    <div class="section general-about">
      <img src="@/assets/about.svg" alt="About Image" class="section-image" />
      <h1>About the Loyalty Rewards Program</h1>
      <p>
        Welcome to Red's Rewards! As a valued member, you will have the
        opportunity to earn points and redeem them for exciting rewards. It's
        our way of saying thank you for being part of our community. The more
        you engage, the more you earn!
      </p>
    </div>

    <div class="section earn-points">
      <img
        src="@/assets/points.svg"
        alt="Earn Points Image"
        class="section-image"
      />
      <h1>How to Earn Points</h1>
      <p>Here are a few ways you can earn points:</p>
      <ul>
        <li data-tooltip="closed">
          <div>
            Sign-up Bonus
            <span class="tooltip"
              >You will earn points by simply signing up for the Red's Rewards
              Program! It's that easy!.</span
            >
          </div>
        </li>

        <li data-tooltip="closed">
          <div>
            Checking-in to the gym
            <span class="tooltip"
              >Every time you check-in at Red's you will earn more
              points!.</span
            >
          </div>
        </li>
        <li data-tooltip="closed">
          <div>
            Refer a friend!
            <span class="tooltip"
              >Refer a friend to Red's and you will receive points!</span
            >
          </div>
        </li>
        <li data-tooltip="closed">
          <div>
            Birthday
            <span class="tooltip"
              >Every year you will receive points for your birthday!</span
            >
          </div>
        </li>
      </ul>
    </div>

    <div class="section rewards">
      <img
        src="@/assets/rewards.svg"
        alt="Rewards Image"
        class="section-image"
      />
      <h1>Rewards</h1>
      <p>Exchange your hard-earned points for these rewards:</p>
      <ul>
        <li>
          Red's T-shirt 200 points
          <span class="tooltip">Earn a free T-shirt!</span>
        </li>
        <li>
          Free Drink/Snack 400
          <span class="tooltip">Get a free drink or snack of your choice!</span>
        </li>
        <li>
          Free Personal Training 750 points
          <span class="tooltip"
            >Get a free 30-minute personal training session!
          </span>
        </li>
        <li>
          Membership Fee Waived for One Month 1000 points
          <span class="tooltip"
            >Enjoy paying no membership fees for one month!</span
          >
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  mounted() {
    const listItems = this.$el.querySelectorAll("li[data-tooltip]");

    listItems.forEach((item) => {
      item.addEventListener("click", (e) => {
        e.stopPropagation();

        // Close any other open tooltips
        listItems.forEach((li) => {
          if (li !== item) li.setAttribute("data-tooltip", "closed");
        });

        // Toggle the current tooltip
        if (item.getAttribute("data-tooltip") === "closed") {
          item.setAttribute("data-tooltip", "open");
        } else {
          item.setAttribute("data-tooltip", "closed");
        }
      });
    });

    // Close tooltip when clicking outside
    document.addEventListener("click", () => {
      listItems.forEach((li) => li.setAttribute("data-tooltip", "closed"));
    });

    document.title = "Red's Rewards - About";
  },
};
</script>

<style scoped>
body,
html {
  height: 100%;
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    "Helvetica Neue", Arial, sans-serif;
  color: #555;
}

body {
  overflow-x: hidden;
}

.about-sections {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 40px;
}

.section {
  position: relative;
  border-radius: 12px;
  padding: 40px;
  margin-bottom: 40px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s;
  max-width: 800px;
  width: 100%;
  background: linear-gradient(135deg, #f5f7fa, #eef2f7);
  border: 1px solid #e1e8ed;
}

.section:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
}

.section-image {
  position: absolute;
  top: -20px;
  right: -20px;
  width: 120px;
  border-radius: 50%;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}

.section:hover .section-image {
  transform: scale(1.1);
}

h1 {
  color: #880000;
  font-size: 28px;
  margin-bottom: 24px;
}

p {
  font-size: 18px;
  line-height: 1.6;
  margin-bottom: 24px;
  color: #7f8c8d;
}

ul {
  padding-left: 24px;
  list-style-type: none;
}

li {
  position: relative;
  font-size: 18px;
  margin-bottom: 14px;
  color: #34495e;
  transition: all 0.3s;
}

li:hover {
  color: #e74c3c;
  cursor: pointer;
  transform: translateX(10px);
  transition: all 0.3s;
}

li:hover .tooltip {
  visibility: visible;
}

.tooltip {
  position: absolute;
  visibility: hidden;
  background-color: rgba(231, 76, 60);
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px 10px;
  z-index: 1;
  bottom: 100%;
  left: 50%;
  transform: translateY(-100%);
  margin-left: 8px;
  white-space: nowrap;
  font-size: 14px;
  box-shadow: 0px 2px 10px rgba(124, 10, 10, 0.1);
}

li[data-tooltip="open"] .tooltip {
  visibility: visible;
}

@media (max-width: 740px) {
  .about-sections {
    padding: 20px, 15px;
    margin-left: 15px;
    margin-right: 15px;
  }

  .section {
    padding: 30px;
  }

  h1 {
    font-size: 24px;
  }

  p,
  li {
    font-size: 16px;
  }

  li:hover {
    transform: none;
  }

  .section-image {
    top: -20px;
    right: 5px;
    width: 60px;
  }

  .tooltip {
    bottom: auto; /* Reset the default bottom positioning */
    top: 100%; /* Position tooltip below the item on mobile */
    left: 50%;
    transform: translateX(-50%); /* Center the tooltip */
    max-width: 200px;
    white-space: normal;
  }
}
</style>
