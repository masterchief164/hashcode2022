#include <bits/stdc++.h>
using namespace std;
#define gc getchar_unlocked
#define fo(i, n) for (i = 0; i < n; i++)
#define Fo(i, k, n) for (i = k; k < n ? i < n : i > n; k < n ? i += 1 : i -= 1)
#define ll long long
#define si(x) scanf("%d", &x)
#define sl(x) scanf("%lld", &x)
#define ss(s) scanf("%s", s)
#define pi(x) printf("%d\n", x)
#define pl(x) printf("%lld\n", x)
#define ps(s) printf("%s\n", s)
#define deb(x) cout << #x << "=" << x << endl
#define deb2(x, y) cout << #x << "=" << x << "," << #y << "=" << y << endl
#define pb push_back
#define mp make_pair
#define F first
#define S second
#define all(x) x.begin(), x.end()
#define clr(x) memset(x, 0, sizeof(x))
#define sortall(x) sort(all(x))
#define tr(it, a) for (auto it = a.begin(); it != a.end(); it++)
#define PI 3.1415926535897932384626
#define endl "\n"
#define YES cout << "YES" \
                 << "\n";
#define NO cout << "NO" \
                << "\n";
typedef pair<ll, ll> pii;
typedef pair<ll, ll> pl;
typedef vector<ll> vi;
typedef vector<ll> vl;
typedef vector<pii> vpii;
typedef vector<pl> vpl;
typedef vector<vi> vvi;
typedef vector<vl> vvl;
mt19937_64 rang(chrono::high_resolution_clock::now().time_since_epoch().count());

struct person
{
    string name;
    ll free = 0;
    vector<pair<string, ll>> skills;
};

struct project
{
    string name;
    ll time;
    ll bestBefore;
    ll score;
    ll numberOfContributors;
    vector<pair<string, ll>> skills;
};

vector<person> people;
vector<project> projects;
vector<pair<string, vector<string>>> ans;

void readfile(string name) // reads all the raw data and converts it into the objects
{
    ll i, n, m, j;
    string s;
    ifstream filena(name); // opens the file

    ll t = 0;
    filena >> n >> m;
    fo(i, n)
    {
        string name;
        ll skills;
        filena >> name >> skills;
        person p;
        p.name = name;
        fo(j, skills)
        {
            string skill;
            ll level;
            filena >> skill >> level;
            p.skills.pb(mp(skill, level));
        }
        people.pb(p);
    }

    fo(i, m)
    {
        string name;
        ll skills, time, bestBefore, score, numberOfContributors;
        filena >> name >> time >> score >> bestBefore >> numberOfContributors;
        project p;
        p.name = name;
        p.time = time;
        p.bestBefore = bestBefore;
        p.score = score;
        fo(j, numberOfContributors)
        {
            string skill;
            ll level;
            filena >> skill >> level;
            p.skills.pb(mp(skill, level));
        }
        projects.pb(p);
    }
    filena >> t;
    fo(i, t)
    {
        filena >> n;
        fo(j, n)
        {
            filena >> s;
        }
        filena >> m;
        fo(j, m)
        {
            filena >> s;
        }
    }
    filena.close(); // file read complete
}

bool comp(const project &a, const project &b)
{
    if (a.bestBefore == b.bestBefore)
        if (a.time == b.time)
            return a.score > b.score;
        else
            return a.time < b.time;
    else
        return a.bestBefore < b.bestBefore;
}

void solve()
{
    ll i, n, m, j;
    sort(all(projects), comp);
    ll time = 0;
    vector<string> working;
    for (auto project : projects) // current project
    {
        if (project.bestBefore - (time + project.time) < 0 && project.score + project.bestBefore - time - project.time < 0) // if score negative
            continue;
        else
        {
            bool isProject = true;        // is doable
            vector<string> names;         // names of people who can do the project
            for (auto s : project.skills) // going through the skills
            {
                bool found = false;        // is the skill found
                string personName = "";    // name of the person
                ll level = LLONG_MAX;      // level of the skill
                for (auto person : people) // going through the people
                {
                    for (auto skill : person.skills) // going through the skills of the person to search
                    {
                        if (skill.F == s.F && skill.S >= s.S && person.free<=time) // is the skill with the level found
                        {
                            found = true;        // skill found
                            if (skill.S < level) // is a person with lower skill found
                            {
                                level = skill.S;          // level of the skill
                                personName = person.name; // name of the person
                            }
                        }
                    }
                }
                if (found) // are all skills found
                {
                    names.pb(personName); // add the name of the person in the names

                    fo(j, people.size())
                    {
                        if (people[j].name == personName)
                        {
                            people[j].free = time + project.time;
                            break;
                        }
                    }
                }
                else
                {
                    isProject = false; // project not doable
                    break;
                }
            }
            if (isProject) // is project doable
            {
                time += project.time; // increase the time
                ans.pb(mp(project.name, names));
            }
        }
    }
}

void printData()
{
    ll i;
    cout << ans.size() << endl;
    for (auto x : ans)
    {
        cout << x.F << "\n";
        for (auto y : x.S)
        {
            cout << y << " ";
        }
        cout << "\n";
    }
    cout << "\n";
}

void write_data(string name)
{
    vl intersectionmap;
    ofstream fileout;
    fileout.open(name);
    ll i = 0;
    fileout << ans.size() << endl;
    for (auto x : ans)
    {
        fileout << x.F << "\n";
        for (auto y : x.S)
        {
            fileout << y << " ";
        }
        fileout << "\n";
    }
    fileout << "\n";
    fileout.close();
}

int main()
{
    string input[] = {"a.in", "b.in", "c.in", "d.in", "e.in", "f.in"};
    string output[] = {"a.out", "b.out", "c.out", "d.out", "e.out", "f.out"};
    ll i;
    fo(i, 6)
    {
        people.clear();
        projects.clear();
        ans.clear();
        readfile(input[i] + ".txt");
        solve();
        printData();
        write_data(output[i] + ".txt");
    }
    return 0;
}