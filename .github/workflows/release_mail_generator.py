from __future__ import print_function

import sys
from collections import defaultdict
from github import Github

# Install dependencies using command pip3 install -r scripts/delivery/requirements.txt
# Run script by running command python3 scripts/delivery/release_mail_generator.py ${token} ${milestone}
class ReleaseMailGenerator:
    MASTER_LABEL_LIST = set(
        ["Collections", "Core Experience", "Growth", "Platform", "Product Initiatives", "User Success", "Translations"]
    )
    PAGE_ITEM_COUNT = 200

    TITLE_TAG_TMPL = '<h2>{}</h2>'
    LABEL_SECTION_TMPL = '<br/><b><i>{}</i></b>'
    LIST_OPEN_TAG = '<ul style="list-style-type:disc">'
    LIST_LINE_ITEM_TMPL = '<li>{}<a target="_blank" href=\"{}\"> (#{})</a> by {}<br/></li>'
    LIST_CLOSE_TAG = '</ul>'
    MESSAGE = '<p>Android app {} has been pushed to Play Store on internal track. Please review the build and give sign off soon</p>'
    INTERNAL_TEST_DISCLAIMER = '<p>Click on the following link to join internal test distribution program on Play Store</p>' + '<a href="https://play.google.com/apps/internaltest/4701420873898948487">https://play.google.com/apps/internaltest/4701420873898948487</a>'

    def get_pull_request_details(self, label, pull_requests_list):
        """

        :param label:
        :param pull_requests_list:
        :return:
        """
        pr_details = self.LABEL_SECTION_TMPL.format(label) + self.LIST_OPEN_TAG
        for pr in pull_requests_list:
            if pr.user.name:
                name = pr.user.name
            else:
                name = pr.user.login
            pr_details += self.LIST_LINE_ITEM_TMPL.format(pr.title, pr.html_url, pr.number, name)
        pr_details += self.LIST_CLOSE_TAG

        return pr_details

    def get_pull_request_label_map(self, repo, issues):
        """

        :param issues:
        :param repo:
        :return:
        """
        pull_request_label_map = defaultdict(list)

        for issue in issues:
            if not issue.pull_request:
                continue

            pr = repo.get_pull(issue.number)
            if not pr.is_merged():
                continue

            valid_label_list = set([label.name for label in issue.labels]).intersection(self.MASTER_LABEL_LIST)

            if not valid_label_list:
                pull_request_label_map['Other'].append(issue)

            for label in valid_label_list:
                pull_request_label_map[label].append(issue)
        return pull_request_label_map

    def get_pull_requests(self, repo, milestone_name):

        try:
            milestones = repo.get_milestones()
            for milestone in milestones:
                if milestone.title == milestone_name:
                    milestone = milestone
                    break
            else:
                print('---------------------------------------------------------------------------------------\n')
                print('Incorrect Milestone, Please Check\n')
                print('----------------------------------------------------------------------------------------')
                return
        except Exception:
            print(e)
            return

        return repo.get_issues(milestone, state="closed")

    def get_changelog(self, auth_token, milestone):
        """

        :param auth_token:
        :param milestone:
        :return:
        """

        github = Github(login_or_token=auth_token, per_page=self.PAGE_ITEM_COUNT)
        repo = github.get_repo("karthikkolanji/ShaadiDemo")
        pull_requests = self.get_pull_requests(repo, milestone)
        if not pull_requests:
            print('---------------------------------------------------------------------------------------\n')
            print('No pull requests found for given Milestone, Please check \n')
            print('----------------------------------------------------------------------------------------')
            return
        pull_request_label_map = self.get_pull_request_label_map(repo, pull_requests)

        if not pull_request_label_map:
            print('---------------------------------------------------------------------------------------\n')
            print('No pull requests is merged for given Milestone, Please check \n')
            print('----------------------------------------------------------------------------------------')
            return

        changelog = self.MESSAGE.format(milestone) + self.INTERNAL_TEST_DISCLAIMER + self.TITLE_TAG_TMPL.format('Changelog')
        for label, pull_requests_list in pull_request_label_map.items():
            changelog += self.get_pull_request_details(label, pull_requests_list)

        return changelog


length = len(sys.argv)

if length < 3:
    print('This script requires two command line areguments\n1. Github access token\n2. Release milestone')
    sys.exit()

print(ReleaseMailGenerator().get_changelog(sys.argv[1], sys.argv[2]))
